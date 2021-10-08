package com.bjpowernode.crm.workbench.service.impl;

import cn.hutool.core.util.StrUtil;
import com.bjpowernode.crm.base.Exception.CrmEnum;
import com.bjpowernode.crm.base.Exception.CrmException;
import com.bjpowernode.crm.base.bean.ResultVo;
import com.bjpowernode.crm.base.util.DateTimeUtil;
import com.bjpowernode.crm.base.util.UUIDUtil;
import com.bjpowernode.crm.workbench.bean.Customer;
import com.bjpowernode.crm.settings.bean.User;
import com.bjpowernode.crm.workbench.bean.CustomerRemark;
import com.bjpowernode.crm.workbench.mapper.CustomerMapper;
import com.bjpowernode.crm.settings.mapper.UserMapper;
import com.bjpowernode.crm.workbench.mapper.CustomerRemarkMapper;
import com.bjpowernode.crm.workbench.service.CustomerService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
@Service
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    private CustomerMapper customerMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private CustomerRemarkMapper customerRemarkMapper;
    @Override
    public List<Customer> list(int page, int pageSize, Customer customer) {
        Example example = new Example(Customer.class);
        Example.Criteria criteria = example.createCriteria();
        //查询客户名称
        if(StrUtil.isNotEmpty(customer.getName())){
            criteria.andLike("name", "%"+ customer.getName()+"%");
        }

        //座机
        if(StrUtil.isNotEmpty(customer.getPhone())){
            criteria.andEqualTo("phone", customer.getPhone());
        }
        //网站
        if(StrUtil.isNotEmpty(customer.getWebsite())){
            criteria.andEqualTo("website", customer.getWebsite());
        }
        //所有者模糊查询
        if(StrUtil.isNotEmpty(customer.getOwner())){
            Example example1 = new Example(User.class);
            Example.Criteria criteria1 = example1.createCriteria();

            criteria1.andLike("name", "%"+ customer.getOwner()+"%");
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
        //查询全部客户,参数一为第几页，参数二为每页有多少个结果,该行代码等同于limit
        PageHelper.startPage(page, pageSize);
        //将查询出来的客户按照创建时间排序
        example.setOrderByClause("createTime desc");

        List<Customer> pageCustomer = customerMapper.selectByExample(example);
        for(Customer customer1:pageCustomer){
            String owner = customer1.getOwner();
            //根据owner在user表中查询数据
            User user = userMapper.selectByPrimaryKey(owner);
            customer1.setOwner(user.getName());
        }
        return pageCustomer;
    }

    @Override
    public ResultVo saveOrUpate(Customer customer, User currentUser) {
        ResultVo resultVo = new ResultVo();
        if (customer.getId() == null  ){
            customer.setId(UUIDUtil.creatUUID());
            customer.setCreateTime(DateTimeUtil.getSysTime());
            customer.setCreateBy(currentUser.getName());
            int i = customerMapper.insertSelective(customer);
            if(i == 0){
                throw new CrmException(CrmEnum.CUSTOMER_SAVE_INFO);
            }
            resultVo.setMessage("添加客户成功");
        }else {
            customer.setEditBy(currentUser.getName());
            customer.setEditTime(DateTimeUtil.getSysTime());
            int i = customerMapper.updateByPrimaryKeySelective(customer);
            if( i== 0){throw new CrmException(CrmEnum.CUSTOMER_UPDATE_INFO);}
            resultVo.setMessage("修改客户成功");
        }
        resultVo.setOk(true);
        return resultVo;
    }

    @Override
    public Customer queryCustomer(String id) {
        Customer customer = customerMapper.selectByPrimaryKey(id);

        return customer;
    }
    @Override
    public Customer queryCustomerByName(String customerName) {
        Customer customer1 = new Customer();
        customer1.setName(customerName);
       Customer select = customerMapper.selectOne(customer1);

        return select;
    }
    @Override
    public void deleteBatch(String ids) {
        String[] split = ids.split(",");
        List<String> strings = Arrays.asList(split);
        Example example = new Example(Customer.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andIn("id", strings);
        int i = customerMapper.deleteByExample(example);
        if(split.length != i){
            throw new CrmException(CrmEnum.CUSTOMER_DELETE_INFO);
        }
    }

    @Override
    public Customer queryDetail(String id) {
        Customer customer = customerMapper.selectByPrimaryKey(id);
        //查询客户备注通过客户的id
        Example example = new Example(CustomerRemark.class);
        example.createCriteria().andEqualTo("customerId",id);
        List<CustomerRemark> customerRemarks = customerRemarkMapper.selectByExample(example);
        customer.setCustomerRemarkList(customerRemarks);
        //设置所有者
        String owner = customer.getOwner();
        User user = userMapper.selectByPrimaryKey(owner);
        customer.setOwner(user.getName());
        return customer;
    }

    @Override
    public void saveRemark(CustomerRemark customerRemark, User currentUser) {
        customerRemark.setId(UUIDUtil.creatUUID());
        customerRemark.setCreateBy(currentUser.getName());
        customerRemark.setCreateTime(DateTimeUtil.getSysTime());
        customerRemark.setImg(currentUser.getImg());
        int i = customerRemarkMapper.insertSelective(customerRemark);
        if (i ==0){
            throw new CrmException(CrmEnum.CUSTOMERREMARK_SAVE_INFO);
        }
    }

    @Override
    public void updateRemark(CustomerRemark customerRemark, User currentUser) {
        customerRemark.setEditBy(currentUser.getName());
        customerRemark.setEditTime(DateTimeUtil.getSysTime());
        int i = customerRemarkMapper.updateByPrimaryKeySelective(customerRemark);
        if (i !=1){
            throw new CrmException(CrmEnum.CUSTOMERREMARK_UPDATE_INFO);
        }
    }

    @Override
    public void deleteRemark(String remarkId) {
        int i = customerRemarkMapper.deleteByPrimaryKey(remarkId);
        if (i != 1){
            throw new CrmException(CrmEnum.CUSTOMERREMARK_DELETE_INFO);
        }
    }
}
