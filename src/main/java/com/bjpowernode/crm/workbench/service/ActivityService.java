package com.bjpowernode.crm.workbench.service;

import cn.hutool.poi.excel.ExcelWriter;
import com.bjpowernode.crm.base.bean.ResultVo;
import com.bjpowernode.crm.workbench.bean.Activity;
import com.bjpowernode.crm.workbench.bean.ActivityRemark;
import com.bjpowernode.crm.settings.bean.User;

import java.util.List;

public interface ActivityService {

    /*public List<Activity> pageActivity(int p,int pageSize);*/

    int count();

    List<Activity> list(int page,int pageSize,Activity activity);

    Activity queryActivity(String id);

    ResultVo saveOrUpate(Activity activity, User currentUser);

    void deleteBatch(String ids);

    Activity queryDetail(String id);

    void saveRemark(ActivityRemark activityRemark, User user);

    void deleteRemark(String remarkId);

    void updateRemark(ActivityRemark activityRemark,User currentUser);

    ExcelWriter exportExcel();

}
