package com.bjpowernode.crm.workbench.service.impl;

import cn.hutool.core.util.StrUtil;
import com.bjpowernode.crm.base.Exception.CrmEnum;
import com.bjpowernode.crm.base.Exception.CrmException;
import com.bjpowernode.crm.base.bean.ResultVo;
import com.bjpowernode.crm.base.util.DateTimeUtil;
import com.bjpowernode.crm.base.util.UUIDUtil;
import com.bjpowernode.crm.settings.bean.User;
import com.bjpowernode.crm.settings.mapper.UserMapper;

import com.bjpowernode.crm.workbench.bean.Contact;
import com.bjpowernode.crm.workbench.bean.ContactRemark;
import com.bjpowernode.crm.workbench.bean.Customer;
import com.bjpowernode.crm.workbench.bean.CustomerRemark;
import com.bjpowernode.crm.workbench.mapper.ContactMapper;
import com.bjpowernode.crm.workbench.mapper.ContactRemarkMapper;
import com.bjpowernode.crm.workbench.mapper.CustomerMapper;
import com.bjpowernode.crm.workbench.service.ContactService;
import com.bjpowernode.crm.workbench.service.CustomerService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class ContactServiceImpl implements ContactService {
    @Autowired
    private ContactMapper contactMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private ContactRemarkMapper contactRemarkMapper;
    @Autowired
    private CustomerMapper customerMapper;
    @Autowired
    private CustomerService customerService;
    @Override
    public List<Contact> selectAll(int page, int pageSize, Contact contact) {
        Example example = new Example(Contact.class);
        Example.Criteria criteria = example.createCriteria();
        //查询联系人
        if(StrUtil.isNotEmpty(contact.getFullname())){
            criteria.andLike("fullname", "%"+ contact.getFullname()+"%");
        }
        //查询customerId
        if(StrUtil.isNotEmpty(contact.getCustomerId())){
            Example example1 = new Example(Customer.class);
            Example.Criteria criteria1 = example1.createCriteria();
            criteria1.andLike("customerName","%"+contact.getCustomerId()+"%");
            //查出来的客户Id
            List<Customer> customers = customerMapper.selectByExample(example1);

                //说明有这家客户公司
                //遍历出相应的客户id集合
                List<String> ids = new ArrayList<>();

                for(Customer customer: customers){
                    ids.add(customer.getId());
                }
                //把客户的id集合作为条件在联系人中查询
                criteria.andIn("customerId", ids);

        }
        //所有者模糊查询
        if(StrUtil.isNotEmpty(contact.getOwner())){
            Example example1 = new Example(User.class);
            Example.Criteria criteria1 = example1.createCriteria();

            criteria1.andLike("name", "%"+ contact.getOwner()+"%");
            //查出来的客户的所有者
            List<User> users = userMapper.selectByExample(example1);
            //遍历出相应的id集合
            List<String> ids = new ArrayList<>();

            for(User user: users){
                ids.add(user.getId());
            }
            //凭借为是拥有者id号为。。。。的市场活动
            criteria.andIn("owner", ids);
        }
        //来源查询
        if(StrUtil.isNotEmpty(contact.getSource())){
            criteria.andEqualTo("source", contact.getSource());
        }
        //生日查询
        if(StrUtil.isNotEmpty(contact.getBirth())){
            criteria.andEqualTo("birth", contact.getBirth());
        }
        //查询全部联系人,参数一为第几页，参数二为每页有多少个结果,该行代码等同于limit
        PageHelper.startPage(page, pageSize);
        //将查询出来的联系人按照创建时间排序
        example.setOrderByClause("createTime");
        List<Contact> pageContact = contactMapper.selectByExample(example);
        for(Contact contact1:pageContact){
            String customerId = contact1.getCustomerId();
            String owner = contact1.getOwner();
            //根据owner在user表中查询数据
            User user = userMapper.selectByPrimaryKey(owner);
            contact1.setOwner(user.getName());
            Customer customerId1 = customerMapper.selectByPrimaryKey(customerId);
            contact1.setCustomerId(customerId1.getName());
            System.out.println(contact1);
        }
        return pageContact;
        }

    @Override
    public ResultVo saveOrUpate(Contact contact, User currentUser) {
        ResultVo resultVo = new ResultVo();
        Customer customer = customerService.queryCustomerByName(contact.getCustomerId());
        if (customer == null ){
            //说明没有这家客户公司，自动创建
            Customer customer1 = new Customer();
            customer1.setName(contact.getCustomerId());
            customerService.saveOrUpate(customer1,currentUser);
        }
        if (contact.getId() == null  ){
            contact.setId(UUIDUtil.creatUUID());
            contact.setCreateTime(DateTimeUtil.getSysTime());
            contact.setCreateBy(currentUser.getName());

            String customerId = contact.getCustomerId();
            Example example = new Example(Customer.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("name", customerId);
            Customer customer1 = customerMapper.selectOneByExample(example);

            contact.setCustomerId(customer1.getId());

            int i = contactMapper.insertSelective(contact);
            if(i == 0){
                throw new CrmException(CrmEnum.CONTACT_SAVE_INFO);
            }
            resultVo.setMessage("添加联系人成功");
        }else {
            contact.setEditBy(currentUser.getName());
            contact.setEditTime(DateTimeUtil.getSysTime());

            String customerId = contact.getCustomerId();
            Example example = new Example(Customer.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("name", customerId);
            Customer customer1 = customerMapper.selectOneByExample(example);
            contact.setCustomerId(customer1.getId());

            int i = contactMapper.updateByPrimaryKeySelective(contact);
            if( i== 0){throw new CrmException(CrmEnum.CONTACT_UPDATE_INFO);}
            resultVo.setMessage("修改联系人成功！");
        }
        resultVo.setOk(true);
        return resultVo;
    }


    @Override
    public Contact queryContact(String id) {
        return contactMapper.selectByPrimaryKey(id);
    }

    @Override
    public void deleteBatch(String ids) {
        String[] split = ids.split(",");
        List<String> strings = Arrays.asList(split);
        Example example = new Example(Contact.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andIn("id", strings);
        int i = contactMapper.deleteByExample(example);
        if(split.length != i){
            throw new CrmException(CrmEnum.CONTACT_DELETE_INFO);
        }
    }

    @Override
    public Contact queryDetail(String id) {
        Contact contact = contactMapper.selectByPrimaryKey(id);
        //查询联系人备注通过联系人的id
        Example example = new Example(ContactRemark.class);
        example.createCriteria().andEqualTo("contactsId",id);
        List<ContactRemark> contactRemarks = contactRemarkMapper.selectByExample(example);
        contact.setContactRemarks(contactRemarks);
        //设置所有者名称
        String owner1 = contact.getOwner();
        User user = userMapper.selectByPrimaryKey(owner1);
        contact.setOwner(user.getName());
        //设置客户名称
        String owner = contact.getCustomerId();
        Customer customer = customerMapper.selectByPrimaryKey(owner);
        contact.setCustomerId(customer.getName());
        return contact;
    }

    @Override
    public void saveRemark(ContactRemark contactRemark, User currentUser) {
        contactRemark.setId(UUIDUtil.creatUUID());
        contactRemark.setCreateBy(currentUser.getName());
        contactRemark.setCreateTime(DateTimeUtil.getSysTime());
        contactRemark.setImg(currentUser.getImg());
        int i = contactRemarkMapper.insertSelective(contactRemark);
        if (i ==0){
            throw new CrmException(CrmEnum.CONTACTREMARK_SAVE_INFO);
        }
    }

    @Override
    public void updateRemark(ContactRemark contactRemark, User currentUser) {
        contactRemark.setEditBy(currentUser.getName());
        contactRemark.setEditTime(DateTimeUtil.getSysTime());
        int i = contactRemarkMapper.updateByPrimaryKeySelective(contactRemark);
        if (i !=1){
            throw new CrmException(CrmEnum.CONTACTREMARK_UPDATE_INFO);
        }
    }

    @Override
    public void deleteRemark(String remarkId) {
        int i = contactRemarkMapper.deleteByPrimaryKey(remarkId);
        if (i != 1){
            throw new CrmException(CrmEnum.CONTACTREMARK_DELETE_INFO);
        }
    }
}
