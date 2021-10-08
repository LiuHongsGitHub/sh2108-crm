package com.bjpowernode.crm.workbench.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.bjpowernode.crm.base.Exception.CrmEnum;
import com.bjpowernode.crm.base.Exception.CrmException;
import com.bjpowernode.crm.base.bean.ResultVo;
import com.bjpowernode.crm.base.util.DateTimeUtil;
import com.bjpowernode.crm.base.util.UUIDUtil;
import com.bjpowernode.crm.workbench.bean.Activity;
import com.bjpowernode.crm.workbench.bean.ActivityRemark;
import com.bjpowernode.crm.settings.bean.User;
import com.bjpowernode.crm.workbench.mapper.ActivityMapper;
import com.bjpowernode.crm.workbench.mapper.ActivityRemarkMapper;
import com.bjpowernode.crm.settings.mapper.UserMapper;
import com.bjpowernode.crm.workbench.service.ActivityService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.EntityColumn;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
public class ActivityServiceImpl implements ActivityService {
    @Autowired
    ActivityMapper activityMapper;
    //原始方式获取所有结果
    @Autowired
    UserMapper userMapper;
    @Autowired
    ActivityRemarkMapper activityRemarkMapper;
    /*@Override
    public List<Activity> pageActivity(int p, int pageSize) {
        List<Activity> pageActivity = activityMapper.getPageActivity(p, pageSize);
        for(Activity activity:pageActivity){
            String owner = activity.getOwner();
            //根据owner在user表中查询数据
            User user = userMapper.selectByPrimaryKey(owner);

            activity.setOwner(user.getName());
        }
        return pageActivity;
    }*/

    @Override
    public int count() {
        return activityMapper.selectAll().size();
    }

    @Override
    public List<Activity> list(int page,int pageSize,Activity activity) {

        Example example = new Example(Activity.class);
        Example.Criteria criteria = example.createCriteria();
        //查询市场活动名称
        if(StrUtil.isNotEmpty(activity.getName())){
            criteria.andLike("name", "%"+ activity.getName()+"%");
        }

        //开始日期
        if(StrUtil.isNotEmpty(activity.getStartDate())){
            criteria.andGreaterThanOrEqualTo("startDate", activity.getStartDate());
        }
        //结束日期
        if(StrUtil.isNotEmpty(activity.getEndDate())){
            criteria.andLessThanOrEqualTo("endDate", activity.getEndDate());
        }
        //所有者模糊查询
        if(StrUtil.isNotEmpty(activity.getOwner())){
            Example example1 = new Example(User.class);
            Example.Criteria criteria1 = example1.createCriteria();

            criteria1.andLike("name", "%"+ activity.getOwner()+"%");
            //查出来的进行该市场活动的所有者
            List<User> users = userMapper.selectByExample(example1);

            if (users.size() != 0){
                //遍历出相应的id集合
                List<String> ids = new ArrayList<>();

                for(User user: users){
                    ids.add(user.getId());
                }
                //凭借为是拥有者id号为。。。。的市场活动
                criteria.andIn("owner", ids);
            }
        }
        //查询全部市场活动,参数一为第几页，参数二为每页有多少个结果,该行代码等同于limit
        PageHelper.startPage(page, pageSize);
        //将查询出来的市场活动按照创建时间排序
        example.setOrderByClause("createTime desc");

        List<Activity> pageActivity = activityMapper.selectByExample(example);
        if (pageActivity.size() !=0){
        for(Activity activity1:pageActivity){
            String owner = activity1.getOwner();
            //根据owner在user表中查询数据
            User user = userMapper.selectByPrimaryKey(owner);
            activity1.setOwner(user.getName());
        }}
        return pageActivity;
    }

    @Override
    public Activity queryActivity(String id) {
        return activityMapper.selectByPrimaryKey(id);
    }

    @Override
    public ResultVo saveOrUpate(Activity activity, User currentUser) {
        ResultVo resultVo = new ResultVo();
        if (activity.getId() == null  ){
            activity.setId(UUIDUtil.creatUUID());
            activity.setCreateTime(DateTimeUtil.getSysTime());
            activity.setCreateBy(currentUser.getName());
            int i = activityMapper.insertSelective(activity);
            if(i == 0){
                throw new CrmException(CrmEnum.ACTIVITY_SAVE_INFO);
            }
            resultVo.setMessage("添加市场活动成功");
        }else {
            activity.setEditBy(currentUser.getName());
            activity.setEditTime(DateTimeUtil.getSysTime());
            int i = activityMapper.updateByPrimaryKeySelective(activity);
            if( i== 0){throw new CrmException(CrmEnum.ACTIVITY_UPDATE_INFO);}
            resultVo.setMessage("修改市场活动成功");
        }
        resultVo.setOk(true);
        return resultVo;
    }

    @Override
    public void deleteBatch(String ids) {
        String[] split = ids.split(",");
        List<String> strings = Arrays.asList(split);
        Example example = new Example(Activity.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andIn("id", strings);
        int i = activityMapper.deleteByExample(example);
        if(split.length != i){
            throw new CrmException(CrmEnum.ACTIVITY_DELETE_INFO);
        }

    }

    @Override
    public Activity queryDetail(String id) {

        Activity activity = activityMapper.selectByPrimaryKey(id);
        Example example = new Example(ActivityRemark.class);
        example.createCriteria().andEqualTo("activityId",id);
        List<ActivityRemark> activityRemarks = activityRemarkMapper.selectByExample(example);
        activity.setActivityRemarkList(activityRemarks);
        String owner = activity.getOwner();
        User user = userMapper.selectByPrimaryKey(owner);
        activity.setOwner(user.getName());
        /*ActivityRemarkMapper
        activity.setActivityRemarkList();*/
        return activity;
    }

    @Override
    public void saveRemark(ActivityRemark activityRemark, User user) {
        Activity activity = activityMapper.selectByPrimaryKey(activityRemark.getActivityId());
        activityRemark.setCreateTime(DateTimeUtil.getSysTime());
        activityRemark.setId( UUIDUtil.creatUUID());
        activityRemark.setCreateBy(user.getName());
        activityRemark.setOwner(userMapper.selectByPrimaryKey( activity.getOwner()).getName());
        activityRemark.setImg(user.getImg());
        int i = activityRemarkMapper.insertSelective(activityRemark);
        if (i != 1){
            throw new CrmException(CrmEnum.ACTIVITYREMARK_SAVE_INFO);
        }
    }

    @Override
    public void updateRemark(ActivityRemark activityRemark,User currentUser ) {
        activityRemark.setEditBy(currentUser.getName());
        activityRemark.setEditTime(DateTimeUtil.getSysTime());
        int i = activityRemarkMapper.updateByPrimaryKeySelective(activityRemark);
        if (i !=1){
            throw new CrmException(CrmEnum.ACTIVITYREMARK_UPDATE_INFO);
        }
    }

    @Override
    public ExcelWriter exportExcel() {

        // 通过工具类创建writer
        ExcelWriter writer = ExcelUtil.getWriter();

        //查询数据
        Example example = new Example(Activity.class);
        Map<String, EntityColumn> propertyMap = example.getPropertyMap();
        List<Activity> activities = activityMapper.selectByExample(example);
        //自定义标题别名
        writer.addHeaderAlias("name", "姓名");
        writer.addHeaderAlias("owner", "所有者");
        writer.addHeaderAlias("startDate", "开始时间");
        writer.addHeaderAlias("endDate", "结束时间");
        writer.addHeaderAlias("cost", "成本");
        writer.addHeaderAlias("description", "描述");
        writer.addHeaderAlias("createTime", "创建时间");
        writer.addHeaderAlias("createBy", "创建者");
        writer.addHeaderAlias("editTime", "编辑时间");
        writer.addHeaderAlias("editBy", "编辑者");

        // 合并单元格后的标题行，使用默认标题样式
        writer.merge(propertyMap.size()-1, "市场活动");
        // 一次性写出内容，使用默认样式，强制输出标题
        writer.write(activities, true);
        return writer;
    }

    @Override
    public void deleteRemark(String remarkId) {
        int i = activityRemarkMapper.deleteByPrimaryKey(remarkId);
        if (i !=1 ){
            throw new CrmException(CrmEnum.ACTIVITYREMARK_DELETE_INFO);
        }
    }
}
