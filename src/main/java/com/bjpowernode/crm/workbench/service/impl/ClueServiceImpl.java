package com.bjpowernode.crm.workbench.service.impl;

import cn.hutool.core.util.StrUtil;
import com.bjpowernode.crm.base.Exception.CrmEnum;
import com.bjpowernode.crm.base.Exception.CrmException;
import com.bjpowernode.crm.base.bean.ResultVo;
import com.bjpowernode.crm.base.util.DateTimeUtil;
import com.bjpowernode.crm.base.util.UUIDUtil;
import com.bjpowernode.crm.settings.bean.User;
import com.bjpowernode.crm.settings.mapper.UserMapper;
import com.bjpowernode.crm.workbench.bean.*;
import com.bjpowernode.crm.workbench.mapper.*;
import com.bjpowernode.crm.workbench.service.ClueService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tk.mybatis.mapper.entity.Example;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
@Service
public class ClueServiceImpl implements ClueService {
    @Autowired
    private ClueMapper clueMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private ClueRemarkMapper clueRemarkMapper;
    @Autowired
    private ClueActivityRelationMapper clueActivityRelationMapper;
    @Autowired
    private ActivityMapper activityMapper;
    @Autowired
    private CustomerMapper customerMapper;
    @Autowired
    private ContactMapper contactMapper;
    @Autowired
    private CustomerRemarkMapper customerRemarkMapper;
    @Autowired
    private ContactRemarkMapper contactRemarkMapper;
    @Autowired
    private ContactActivityRelationMapper contactActivityRelationMapper;
    @Autowired
    private TranMapper tranMapper;
    @Autowired
    private TranHistoryMapper tranHistoryMapper;
    @Autowired
    private TranRemarkMapper tranRemarkMapper;
    @Override
    public List<Clue> list(int page, int pageSize, Clue clue) {
        Example example = new Example(Contact.class);
        Example.Criteria criteria = example.createCriteria();
        //查询线索
        if (StrUtil.isNotEmpty(clue.getFullname())) {
            criteria.andLike("fullname", "%" + clue.getFullname() + "%");
        }
        //查询公司名字
        if (StrUtil.isNotEmpty(clue.getCompany())) {
            criteria.andLike("company", "%" + clue.getCompany() + "%");
        }
        //所有者模糊查询

        if (StrUtil.isNotEmpty(clue.getOwner())) {
            Example example1 = new Example(User.class);
            Example.Criteria criteria1 = example1.createCriteria();

            criteria1.andLike("name", "%" + clue.getOwner() + "%");
            //查出来的客户的所有者
            List<User> users = userMapper.selectByExample(example1);
            //遍历出相应的id集合
            List<String> ids = new ArrayList<>();

            for (User user : users) {
                ids.add(user.getId());
            }
            //凭借为是拥有者id号为。。。。的
            criteria.andIn("owner", ids);
        }
        //来源查询
        if (StrUtil.isNotEmpty(clue.getSource())) {
            criteria.andEqualTo("source", clue.getSource());
        }
        //手机查询
        if (StrUtil.isNotEmpty(clue.getPhone())) {
            criteria.andEqualTo("phone", clue.getPhone());
        }
        //座机查询
        if (StrUtil.isNotEmpty(clue.getMphone())) {
            criteria.andEqualTo("mphone", clue.getMphone());
        }
        //线索状态
        if (StrUtil.isNotEmpty(clue.getState())) {
            //线索查询不为空
            criteria.andEqualTo("state", clue.getState());
        }
            //查询全部线索,参数一为第几页，参数二为每页有多少个结果,该行代码等同于limit
            PageHelper.startPage(page, pageSize);
            //将查询出来的线索按照创建时间排序
            example.setOrderByClause("createTime");
            List<Clue> pageClue = clueMapper.selectByExample(example);
            for (Clue clue1 : pageClue) {
                String owner = clue1.getOwner();
                //根据owner在user表中查询数据
                User user = userMapper.selectByPrimaryKey(owner);
                clue1.setOwner(user.getName());
            }
            return pageClue;
    }

    @Override
    public ResultVo saveOrUpate(Clue clue, User currentUser) {
        ResultVo resultVo = new ResultVo();
        if (clue.getId() == null  ){
            clue.setId(UUIDUtil.creatUUID());
            clue.setCreateTime(DateTimeUtil.getSysTime());
            clue.setCreateBy(currentUser.getName());
            int i = clueMapper.insertSelective(clue);
            if(i == 0){
                throw new CrmException(CrmEnum.CLUE_SAVE_INFO);
            }
            resultVo.setMessage("添加线索成功!");
        }else {
            clue.setEditBy(currentUser.getName());
            clue.setEditTime(DateTimeUtil.getSysTime());

            int i = clueMapper.updateByPrimaryKeySelective(clue);
            if( i== 0){throw new CrmException(CrmEnum.CLUE_UPDATE_INFO);}
            resultVo.setMessage("修改线索成功！");
        }
        resultVo.setOk(true);
        return  resultVo;
    }

    @Override
    public Clue queryClue(String id) {
        return clueMapper.selectByPrimaryKey(id);
    }

    @Override
    public void deleteBatch(String ids) {
        String[] split = ids.split(",");
        List<String> strings = Arrays.asList(split);
        Example example = new Example(Clue.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andIn("id", strings);
        int i = clueMapper.deleteByExample(example);
        if(split.length != i){
            throw new CrmException(CrmEnum.CLUE_DELETE_INFO);
        }
    }

    @Override
    public Clue queryDetail(String id) {
        Clue clue   = clueMapper.selectByPrimaryKey(id);
        //通过线索id找到线索备注
        Example example = new Example(ClueRemark.class);
        example.createCriteria().andEqualTo("clueId", id);
        List<ClueRemark> clueRemarks = clueRemarkMapper.selectByExample(example);
        if (clueRemarks.size() != 0){
        clue.setClueRemarks(clueRemarks);}
        //CLUE的所有者也需要设置
        User user = userMapper.selectByPrimaryKey(clue.getOwner());
        clue.setOwner(user.getName());

        //查询并设置市场活动
        Example example1 = new Example(ClueActivityRelation.class);
        example1.createCriteria().andEqualTo("clueId", id);
        List<ClueActivityRelation> clueActivityRelations = clueActivityRelationMapper.selectByExample(example1);
        if (clueActivityRelations.size() != 0){
        List<String> ids = new ArrayList<>();
        for (ClueActivityRelation clueActivityRelation:clueActivityRelations){
            ids.add(clueActivityRelation.getActivityId());
        }
        Example example2 = new Example(Activity.class);
        example2.createCriteria().andIn("id", ids);
        List<Activity> activities = activityMapper.selectByExample(example2);
        clue.setActivities(activities);}
        return  clue;
    }

    @Override
    public void saveRemark(ClueRemark clueRemark, User currentUser) {
        clueRemark.setId(UUIDUtil.creatUUID());
        clueRemark.setCreateBy(currentUser.getName());
        clueRemark.setCreateTime(DateTimeUtil.getSysTime());

        clueRemark.setImg(currentUser.getImg());
        int insert = clueRemarkMapper.insert(clueRemark);
        if (insert !=1){
            throw new CrmException(CrmEnum.CLUEREMARK_SAVE_INFO);
        }

    }
    @Override
    public void updateRemark(ClueRemark clueRemark, User currentUser) {
        clueRemark.setEditBy(currentUser.getName());
        clueRemark.setEditTime(DateTimeUtil.getSysTime());
        int i = clueRemarkMapper.updateByPrimaryKeySelective(clueRemark);
        if (i !=1){
            throw new CrmException(CrmEnum.CLUEREMARK_UPDATE_INFO);
        }
    }

    @Override
    public void deleteRemark(String remarkId) {
        int i = clueRemarkMapper.deleteByPrimaryKey(remarkId);
        if (i != 1){
            throw new CrmException(CrmEnum.CLUEREMARK_DELETE_INFO);
        }
    }
    //传进来的是线索ID，关联多个市场活动
    @Override
    public List<Activity> queryActivities(String id,String name) {

            //通过线索id查询已关联市场活动id
            Example example1 = new Example(ClueActivityRelation.class);
            example1.createCriteria().andEqualTo("clueId", id);
            List<ClueActivityRelation> clueActivityRelations = clueActivityRelationMapper.selectByExample(example1);

            Example example = new Example(Activity.class);
            Example.Criteria id1 = example.createCriteria();
            if (clueActivityRelations.size()!=0){
            List<String> ids = new ArrayList<>();
            for (ClueActivityRelation clueActivityRelation: clueActivityRelations){
                String activityId = clueActivityRelation.getActivityId();
                ids.add(activityId);
            }

            id1.andNotIn("id", ids);}
            if (StrUtil.isNotEmpty(name)){
                //名字不为空，模糊查询市场活动
                id1.andLike("name", "%"+ name+ "%");
            }
            List<Activity> activities = activityMapper.selectByExample(example);

            return activities;
    }

    @Override
    public List<Activity> bind(String id, String ids) {
        //先将ids切成字符串
        String[] split = ids.split(",");
        //创建集合接收市场活动
        List<Activity> activities = new ArrayList<>();

        for (String idd : split){
            ClueActivityRelation clueActivityRelation = new ClueActivityRelation();
            clueActivityRelation.setClueId(id);
            clueActivityRelation.setId(UUIDUtil.creatUUID());
            clueActivityRelation.setActivityId(idd);
            int i = clueActivityRelationMapper.insertSelective(clueActivityRelation);
            if (i != 0){
                throw new CrmException(CrmEnum.CLUEACTIVITYRELATION_INSERT_INFO);
            }
            Activity activity = activityMapper.selectByPrimaryKey(idd);
            activities.add(activity);
        }

        return activities;
    }

    @Override
    public void unbind(ClueActivityRelation clueActivityRelation) {
        int delete = clueActivityRelationMapper.delete(clueActivityRelation);
        if (delete != 1){
            throw new CrmException(CrmEnum.CLUEACTIVITYRELATION_DELETE_INFO);
        }
    }

    @Override
    public List<Activity> queryBindActivities(String id, String name) {
        ClueActivityRelation clueActivityRelation = new ClueActivityRelation();
        clueActivityRelation.setClueId(id);
        //查询绑定的市场活动
        List<ClueActivityRelation> clueActivityRelations = clueActivityRelationMapper.select(clueActivityRelation);
        List<String> activityIds = new ArrayList<>();
        for (ClueActivityRelation clueActivityRelation1:clueActivityRelations){
            activityIds.add(clueActivityRelation1.getActivityId());
        }
        Example example = new Example(Activity.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andIn("id", activityIds);
        if (StrUtil.isNotEmpty(name)){
            criteria.andLike("name", "%"+name+ "%");
        }

        List<Activity> activities = activityMapper.selectByExample(example);
        for (Activity activity:activities){
            String owner = activity.getOwner();
            User user = userMapper.selectByPrimaryKey(owner);
            activity.setOwner(user.getName());
        }
        return activities;
    }

    @Override
    public void convert(String isCreateTransaction, Tran tran, String clueId, User user) {
        int count;
        //根据线索主键查询线索的信息
        Clue clue = clueMapper.selectByPrimaryKey(clueId);
        //将线索中的客户信息取出来保存在客户表，不存在则新建
        Customer customer = new Customer();
        customer.setName(clue.getCompany());
        List<Customer> select = customerMapper.select(customer);
        if (select.size() ==0){
            //新建
            customer.setId(UUIDUtil.creatUUID());
            customer.setOwner(clue.getOwner());
            customer.setCreateBy(user.getName());
            customer.setCreateTime(DateTimeUtil.getSysTime());
            customer.setPhone(clue.getPhone());
            customer.setNextContactTime(clue.getNextContactTime());
            customer.setDescription(clue.getDescription());
            customer.setContactSummary(clue.getContactSummary());
            customer.setWebsite(clue.getWebsite());
            count = customerMapper.insertSelective(customer);
            if (count == 0){
                throw new CrmException(CrmEnum.CLUE_CONVERT);
            }
        }else {
            //客户存在，直接取出
            customer = select.get(0);
        }
        //将联系人取出
        Contact contact = new Contact();
        contact.setId(UUIDUtil.creatUUID());
        contact.setOwner(clue.getOwner());
        contact.setSource(clue.getSource());
        contact.setCustomerId(customer.getId());
        contact.setFullname(clue.getFullname());
        contact.setAppellation(clue.getFullname());
        contact.setEmail(clue.getEmail());
        contact.setMphone(clue.getMphone());
        contact.setJob(clue.getJob());

        contact.setCreateBy(user.getName());
        contact.setCreateTime(DateTimeUtil.getSysTime());
        contact.setDescription(clue.getDescription());
        contact.setContactSummary(clue.getContactSummary());
        contact.setNextContactTime(clue.getNextContactTime());
        contact.setAddress(clue.getAddress());

        count = contactMapper.insertSelective(contact);
        if (count==0){
            throw new CrmException(CrmEnum.CLUE_CONVERT);
        }
        //线索中的备注信息取出来保存在客户备注和联系人备注中
        //客户备注
        List<ClueRemark> clueRemarks = clue.getClueRemarks();
        ContactRemark contactRemark = new ContactRemark();

        contactRemark.setContactsId(contact.getId());
        CustomerRemark customerRemark = new CustomerRemark();
        customerRemark.setCustomerId(customer.getId());

        List<CustomerRemark> customerRemarks = customerRemarkMapper.select(customerRemark);
            if (customerRemarks.size()!=0){
                for (CustomerRemark customerRemark1 : customerRemarks){
                    customerRemark.setId(UUIDUtil.creatUUID());
                    customerRemark.setCreateBy(user.getName());
                    customerRemark.setCreateTime(DateTimeUtil.getSysTime());
                    count = customerRemarkMapper.insertSelective(customerRemark1);
                    if(count == 0){
                        throw new CrmException(CrmEnum.CLUE_CONVERT);
                    }
                }
            }

        List<ContactRemark> contactRemarks = contactRemarkMapper.select(contactRemark);
            if (contactRemarks.size()!=0){
                for (ContactRemark contactRemark1 : contactRemarks){
                    contactRemark.setId(UUIDUtil.creatUUID());
                    contactRemark.setCreateBy(user.getName());
                    contactRemark.setCreateTime(DateTimeUtil.getSysTime());
                    count = contactRemarkMapper.insertSelective(contactRemark1);
                    if(count == 0){
                        throw new CrmException(CrmEnum.CLUE_CONVERT);
                    }
                }
            }
        //5、将"线索和市场活动的关系"转换到"联系人和市场活动的关系中"
        ClueActivityRelation clueActivityRelation = new ClueActivityRelation();
            clueActivityRelation.setClueId(clueId);
        List<ClueActivityRelation> clueActivityRelations = clueActivityRelationMapper.select(clueActivityRelation);
        for (ClueActivityRelation clueActivityRelation1: clueActivityRelations){
            ContactActivityRelation contactActivityRelation = new ContactActivityRelation();
            contactActivityRelation.setId(UUIDUtil.creatUUID());
            contactActivityRelation.setContactsId(contact.getId());
            contactActivityRelation.setActivityId(clueActivityRelation1.getActivityId());
            count = contactActivityRelationMapper.insertSelective(contactActivityRelation);
            if(count == 0){
                throw new CrmException(CrmEnum.CLUE_CONVERT);
            }
        }
        //6、如果转换过程中发生了交易，创建一条新的交易，交易信息不全，后面可以通过修改交易来完善交易信息
        //判断值为1，则创建新的交易
        if ("1".equals(isCreateTransaction)){
            tran.setId(UUIDUtil.creatUUID());
            tran.setCreateBy(user.getName());
            tran.setCreateTime(DateTimeUtil.getSysTime());
            tran.setCustomerId(customer.getId());

            count = tranMapper.insertSelective(tran);
            if (count==0){
                throw new CrmException(CrmEnum.CLUE_CONVERT);
            }
            //7、创建该条交易对应的交易历史以及备注
            //插入交易历史
            TranHistory tranHistory = new TranHistory();
            tranHistory.setCreateBy(user.getName());
            tranHistory.setCreateTime(DateTimeUtil.getSysTime());
            tranHistory.setId(UUIDUtil.creatUUID());
            tranHistory.setStage(tran.getStage());
            tranHistory.setMoney(tran.getMoney());
            tranHistory.setExpectedDate(tran.getExpectedDate());
            tranHistory.setTranId(tran.getId());
            count = tranHistoryMapper.insertSelective(tranHistory);
            if (count== 0){
                throw new CrmException(CrmEnum.CLUE_CONVERT);
            }
            //插入交易备注
            TranRemark tranRemark = new TranRemark();
            tranRemark.setCreateBy(user.getName());
            tranRemark.setCreateTime(DateTimeUtil.getSysTime());
            tranRemark.setId(UUIDUtil.creatUUID());
            tranRemark.setTranId(tran.getId());
            count = tranRemarkMapper.insertSelective(tranRemark);
            if (count== 0){
                throw new CrmException(CrmEnum.CLUE_CONVERT);
            }
            //8、删除线索的备注信息
            ClueRemark clueRemark = new ClueRemark();
            clueRemark.setClueId(clueId);
            count = clueRemarkMapper.delete(clueRemark);
            if (count== 0){
                throw new CrmException(CrmEnum.CLUE_CONVERT);
            }
            //删除线索和市场活动的关联关系(tbl_clue_activity_relation)
            ClueActivityRelation clueActivityRelation3 =new ClueActivityRelation();
            clueActivityRelation3.setClueId(clueId);
            count = clueActivityRelationMapper.delete(clueActivityRelation3);
            if (count== 0){
                throw new CrmException(CrmEnum.CLUE_CONVERT);
            }
            //删除线索
            count = clueMapper.deleteByPrimaryKey(clueId);
            if (count== 0){
                throw new CrmException(CrmEnum.CLUE_CONVERT);
            }

        }


    }
}
