package com.bjpowernode.crm.workbench.service.impl;

import cn.hutool.core.util.StrUtil;
import com.bjpowernode.crm.base.Exception.CrmEnum;
import com.bjpowernode.crm.base.Exception.CrmException;
import com.bjpowernode.crm.base.util.DateTimeUtil;
import com.bjpowernode.crm.base.util.UUIDUtil;
import com.bjpowernode.crm.settings.bean.User;
import com.bjpowernode.crm.settings.mapper.UserMapper;
import com.bjpowernode.crm.workbench.bean.*;
import com.bjpowernode.crm.workbench.mapper.ContactMapper;
import com.bjpowernode.crm.workbench.mapper.CustomerMapper;
import com.bjpowernode.crm.workbench.mapper.TranHistoryMapper;
import com.bjpowernode.crm.workbench.mapper.TranMapper;
import com.bjpowernode.crm.workbench.service.TranService;

import com.github.pagehelper.PageHelper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TranServiceImpl implements TranService {
    @Autowired
    private CustomerMapper customerMapper;
    @Autowired
    private TranMapper tranMapper;
    @Autowired
    private UserMapper userMapper ;
    @Autowired
    private ContactMapper contactMapper ;
    @Autowired
    private TranHistoryMapper tranHistoryMapper ;
    @Override
    public List<String> queryCustomerName(String customerName) {
        Example example = new Example(Customer.class);
        example.createCriteria().andLike("name", "%"+customerName+"%");
        List<Customer> customers = customerMapper.selectByExample(example);
        List<String> customerNames = new ArrayList<>();
        for (Customer customer: customers){
            customerNames.add(customer.getName());
        }
        return customerNames;
    }

    @Override
    public List<Tran> list(Tran tran, int page, int pageSize) {
        Example example = new Example(Tran.class);
        Example.Criteria criteria = example.createCriteria();
        if (StrUtil.isNotEmpty(tran.getOwner())){
            User user = new User();
            user.setName(tran.getOwner());
            List<User> users = userMapper.select(user);
            User user1 = users.get(0);
            if (users.size() != 0 ){
                criteria.andLike("owner", "%"+user1+"%");
            }
        }
        if (StrUtil.isNotEmpty(tran.getName())){
                criteria.andLike("name", "%"+tran.getName()+"%");
        }
        if (StrUtil.isNotEmpty(tran.getCustomerId())){
            Customer customer = new Customer();
            customer.setName(tran.getCustomerId());
            Customer customer1 = customerMapper.selectOne(customer);
            if (customer1!=null){
                criteria.andEqualTo("customerId", customer1.getId());
            }
        }
        //阶段
        if (StrUtil.isNotEmpty(tran.getStage())){
            criteria.andEqualTo("stage", tran.getStage());
        }

        //类型
        if (StrUtil.isNotEmpty(tran.getType())){
            criteria.andEqualTo("type", tran.getType());
        }
        //来源
        if (StrUtil.isNotEmpty(tran.getSource())){
            criteria.andEqualTo("source", tran.getSource());
        }
        //联系人名称
        if (StrUtil.isNotEmpty(tran.getContactsId())){
            Contact contact = new Contact();
            contact.setFullname(tran.getContactsId());
            List<Contact> contacts = contactMapper.select(contact);
            List<String> ids = new ArrayList<>();
            for (Contact contact1: contacts){
                ids.add(contact1.getId());
            }
            if (contacts.size()!=0){

                criteria.andIn("contactsId", ids);
            }
        }


        PageHelper.startPage(page, pageSize);
        example.setOrderByClause("createTime");

        List<Tran> pageTran = tranMapper.selectByExample(example);
        for (Tran tran1: pageTran){
            String customerId = tran1.getCustomerId();
            String owner = tran1.getOwner();
            //根据owner在user表中查询数据
            User user = userMapper.selectByPrimaryKey(owner);
            tran1.setOwner(user.getName());
            Customer customerId1 = customerMapper.selectByPrimaryKey(customerId);
            tran1.setCustomerId(customerId1.getName());
        }

        return pageTran;
    }

    @Override
    public Map<String, Object> queryStage(Integer location,String id, Map<String,String> stage2Possibility, User user) {

        /*查询出当前交易的信息
        取出阶段，查看可能性，如果是0，则前7个图标都为黑圈，后两个进行比较，是什么问题失败
        不为0
        遍历取出交易中的阶段进行比较，如果相同则设置锚点
        描点之前为绿圈
        判断是否是第7个图标，第7个之后则是*/
        Tran tran = tranMapper.selectByPrimaryKey(id);
        String currentStage = null;
        String currentPossibility = null;
        //将map转换成List进行遍历,用于与传进来的阶段进行比较
        List<Map.Entry<String,String>> stage2Possibilitys = new ArrayList<>(stage2Possibility.entrySet());
        Map<String, Object> map = new HashMap<>();
        if (location == null){
            //第一次，无指定阶段
            currentStage = tran.getStage();
            currentPossibility = tran.getPossibility();
        }else {
            //点击了图表=标
            currentStage = stage2Possibilitys.get(location).getKey();
            currentPossibility = stage2Possibilitys.get(location).getValue();
            //点击图标则进行添加交易历史
            TranHistory tranHistory = new TranHistory();

            tranHistory.setId(UUIDUtil.creatUUID());
            tranHistory.setStage(currentStage);
            tranHistory.setMoney(tran.getMoney());
            tranHistory.setExpectedDate(tran.getExpectedDate());
            tranHistory.setCreateTime(DateTimeUtil.getSysTime());
            tranHistory.setCreateBy(user.getName());
            tranHistory.setTranId(id);
            tranHistory.setPossibility(currentPossibility);
            int i = tranHistoryMapper.insertSelective(tranHistory);
            if (i==0){
                throw new CrmException(CrmEnum.TRANHISTORY_SAVE_INFO);
            }
            map.put("tranHistory", tranHistory);
        }
        map.put("index",location);
        int index = 0 ;//确定锚点的位置
        int point = 0;//红叉的位置

        for (int i = 0 ; i<stage2Possibilitys.size();i++){
            String oleStage = stage2Possibilitys.get(i).getKey();
            String possibility = stage2Possibilitys.get(i).getValue();
            if (oleStage.equals(currentStage)){
                index = i;
            }
            if ("0".equals(possibility)){
                //失败的交易,红叉的位置为index
                point = i;
                //停止循环
                break;
            }
        }
        List<StageVo> stageVos = new ArrayList<>();
        if ("0".equals(currentPossibility)){
            //可能性为0，说明是失败，判断红叉的位置为point,前7个为黑圈
            //遍历所有阶段
            for (int i = 0 ; i<stage2Possibilitys.size();i++){
                StageVo stageVo = new StageVo();
                String stage = stage2Possibilitys.get(i).getKey();
                String possibility = stage2Possibilitys.get(i).getValue();
                //找到可能性为0的两个阶段
                if ("0".equals(possibility)){
                    //判断是哪个失败原因
                    if (stage.equals(currentStage)){
                        //红叉所在阶段
                        stageVo.setType("红叉");
                    }else {
                        //黑叉所在
                        stageVo.setType("黑叉");
                    }
                }else {
                    //可能性不为0，黑圈
                     stageVo.setType("黑圈");

                }
                stageVo.setStage(stage);
                stageVo.setPossibility(possibility);
                stageVos.add(stageVo);
            }
        }else {
            //交易未失败，确定锚点位置
            for (int i = 0 ; i<stage2Possibilitys.size();i++){
                StageVo stageVo = new StageVo();
                String stage = stage2Possibilitys.get(i).getKey();
                String possibility = stage2Possibilitys.get(i).getValue();
                if (index == i){
                    //锚点位置
                    stageVo.setType("锚点");
                }else if (index > i){
                    //绿圈
                    stageVo.setType("绿圈");
                }else if (i>index && i<point){
                    //黑圈
                    stageVo.setType("黑圈");
                }else{
                    stageVo.setType("黑叉");
                }
                stageVo.setStage(stage);
                stageVo.setPossibility(possibility);
                stageVos.add(stageVo);
            }
        }

        map.put("stageVos", stageVos);
        return map;
    }
}
