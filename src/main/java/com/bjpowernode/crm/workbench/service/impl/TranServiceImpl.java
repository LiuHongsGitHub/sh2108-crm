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
        //??????
        if (StrUtil.isNotEmpty(tran.getStage())){
            criteria.andEqualTo("stage", tran.getStage());
        }

        //??????
        if (StrUtil.isNotEmpty(tran.getType())){
            criteria.andEqualTo("type", tran.getType());
        }
        //??????
        if (StrUtil.isNotEmpty(tran.getSource())){
            criteria.andEqualTo("source", tran.getSource());
        }
        //???????????????
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
            //??????owner???user??????????????????
            User user = userMapper.selectByPrimaryKey(owner);
            tran1.setOwner(user.getName());
            Customer customerId1 = customerMapper.selectByPrimaryKey(customerId);
            tran1.setCustomerId(customerId1.getName());
        }

        return pageTran;
    }

    @Override
    public Map<String, Object> queryStage(Integer location,String id, Map<String,String> stage2Possibility, User user) {

        /*??????????????????????????????
        ??????????????????????????????????????????0?????????7?????????????????????????????????????????????????????????????????????
        ??????0
        ????????????????????????????????????????????????????????????????????????
        ?????????????????????
        ??????????????????7???????????????7???????????????*/
        Tran tran = tranMapper.selectByPrimaryKey(id);
        String currentStage = null;
        String currentPossibility = null;
        //???map?????????List????????????,???????????????????????????????????????
        List<Map.Entry<String,String>> stage2Possibilitys = new ArrayList<>(stage2Possibility.entrySet());
        Map<String, Object> map = new HashMap<>();
        if (location == null){
            //???????????????????????????
            currentStage = tran.getStage();
            currentPossibility = tran.getPossibility();
        }else {
            //???????????????=???
            currentStage = stage2Possibilitys.get(location).getKey();
            currentPossibility = stage2Possibilitys.get(location).getValue();
            //???????????????????????????????????????
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
        int index = 0 ;//?????????????????????
        int point = 0;//???????????????

        for (int i = 0 ; i<stage2Possibilitys.size();i++){
            String oleStage = stage2Possibilitys.get(i).getKey();
            String possibility = stage2Possibilitys.get(i).getValue();
            if (oleStage.equals(currentStage)){
                index = i;
            }
            if ("0".equals(possibility)){
                //???????????????,??????????????????index
                point = i;
                //????????????
                break;
            }
        }
        List<StageVo> stageVos = new ArrayList<>();
        if ("0".equals(currentPossibility)){
            //????????????0?????????????????????????????????????????????point,???7????????????
            //??????????????????
            for (int i = 0 ; i<stage2Possibilitys.size();i++){
                StageVo stageVo = new StageVo();
                String stage = stage2Possibilitys.get(i).getKey();
                String possibility = stage2Possibilitys.get(i).getValue();
                //??????????????????0???????????????
                if ("0".equals(possibility)){
                    //???????????????????????????
                    if (stage.equals(currentStage)){
                        //??????????????????
                        stageVo.setType("??????");
                    }else {
                        //????????????
                        stageVo.setType("??????");
                    }
                }else {
                    //???????????????0?????????
                     stageVo.setType("??????");

                }
                stageVo.setStage(stage);
                stageVo.setPossibility(possibility);
                stageVos.add(stageVo);
            }
        }else {
            //????????????????????????????????????
            for (int i = 0 ; i<stage2Possibilitys.size();i++){
                StageVo stageVo = new StageVo();
                String stage = stage2Possibilitys.get(i).getKey();
                String possibility = stage2Possibilitys.get(i).getValue();
                if (index == i){
                    //????????????
                    stageVo.setType("??????");
                }else if (index > i){
                    //??????
                    stageVo.setType("??????");
                }else if (i>index && i<point){
                    //??????
                    stageVo.setType("??????");
                }else{
                    stageVo.setType("??????");
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
