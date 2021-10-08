package com.bjpowernode.crm.workbench.controller;

import cn.hutool.core.io.IoUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.bjpowernode.crm.base.Exception.CrmException;
import com.bjpowernode.crm.base.bean.ResultVo;
import com.bjpowernode.crm.base.util.CommonUtil;
import com.bjpowernode.crm.workbench.bean.Activity;
import com.bjpowernode.crm.workbench.bean.ActivityRemark;
import com.bjpowernode.crm.settings.bean.User;
import com.bjpowernode.crm.workbench.service.ActivityService;
import com.bjpowernode.crm.settings.service.UserService;

import com.github.pagehelper.PageInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class ActivityController {
    @Autowired
    ActivityService activityService;
    @Autowired
    UserService userService;

    //1、原始方法
    /*@RequestMapping("/workbench/activity/list")
    @ResponseBody
    public ResultVo activityList(@RequestParam(defaultValue ="1") int p){
        ResultVo resultVo =new ResultVo();
        //查询全部市场活动,参数一为第几页，参数二为每页有多少个结果

        List<Activity> activities = activityService.pageActivity((p - 1) * 3,pageSize);
        //市场活动的总记录数
        int totalRecord = activityService.count();

        //总页数
        int  pages= totalRecord % pageSize == 0 ? totalRecord/pageSize : totalRecord/pageSize + 1 ;
        //放入结果集中返回,所有市场活动对象，总页数
        resultVo.setT(pages);
        resultVo.setList(activities);
        return resultVo;
    }*/

    //使用分页插件进行分页
    @RequestMapping("/workbench/activity/list")
    @ResponseBody
    public PageInfo activityList(int page,int pageSize,Activity activity){


        List<Activity> activities =  activityService.list(page, pageSize, activity);

        PageInfo<Activity> activityPageInfo = new PageInfo<>(activities);

        return activityPageInfo;
    }
    //查询全部所有者的信息
    @RequestMapping("/workbench/activity/queryUsers")
    @ResponseBody
    public List<User> queryUsers(){
        List<User> users = userService.queryAllUser();
        return users;
    }
    //保存或更新市场活动
    @RequestMapping("/workbench/activity/saveAndUpdate")
    @ResponseBody
    public ResultVo saveAndUpdate(Activity activity,HttpSession session){
        ResultVo resultVo =new ResultVo();
        //判断是创建还是修改
            try {
                User currentUser = CommonUtil.getCurrentUser(session);
                resultVo = activityService.saveOrUpate(activity,currentUser);
            } catch (CrmException e) {
                resultVo.setMessage(e.getMessage());
            }
        return resultVo;
    }
    //查询单个市场活动
    @RequestMapping("/workbench/activity/queryActivity")
    @ResponseBody
    public Activity queryActivity(String id){

        Activity activities =  activityService.queryActivity(id);
        return activities;
    }
    //删除数据
    @RequestMapping("/workbench/activity/deleteBatch")
    @ResponseBody
    public ResultVo deleteBatch(String ids){
        ResultVo resultVo =new ResultVo();

        try {
            activityService.deleteBatch(ids);
            resultVo.setOk(true);
            resultVo.setMessage("删除市场活动成功！");
        } catch (CrmException e) {
            resultVo.setMessage(e.getMessage());
        }
        return resultVo;
    }
    //查询市场活动及备注
    @RequestMapping("/workbench/activity/queryDetail")
    @ResponseBody
    public Activity queryDetail(String id ){
        Activity activity = activityService.queryDetail(id);
        return activity;
    }
    //保存备注
    @RequestMapping("/workbench/activity/saveRemark")
    @ResponseBody
    public ResultVo saveRemark(ActivityRemark activityRemark, HttpSession session){
        ResultVo resultVo =new ResultVo();
        User currentUser = CommonUtil.getCurrentUser(session);
        try {
            activityService.saveRemark(activityRemark,currentUser);
            resultVo.setOk(true);
            resultVo.setMessage("添加市场活动备注成功！");
        } catch (CrmException e) {
            resultVo.setMessage(e.getMessage());
        }
        return resultVo;
    }
    //更新备注
    @RequestMapping("/workbench/activity/updateRemark")
    @ResponseBody
    public ResultVo updateRemark(ActivityRemark activityRemark,HttpSession session){
        ResultVo resultVo =new ResultVo();
        User currentUser = CommonUtil.getCurrentUser(session);
        try {
            activityService.updateRemark(activityRemark,currentUser);
            resultVo.setOk(true);
            resultVo.setMessage("更新市场活动备注成功！");
        } catch (CrmException e) {
            resultVo.setMessage(e.getMessage());
        }
        return resultVo;
    }
    //删除备注
    @RequestMapping("/workbench/activity/deleteRemark")
    @ResponseBody
    public ResultVo deleteRemark(String remarkId){
        ResultVo resultVo =new ResultVo();

        try {
            activityService.deleteRemark(remarkId);
            resultVo.setOk(true);
            resultVo.setMessage("删除市场活动备注成功！");
        } catch (CrmException e) {
            resultVo.setMessage(e.getMessage());
        }
        return resultVo;
    }
    //导出报表
    @RequestMapping("/workbench/activity/exportExcel")
    public void exportExcel(HttpServletResponse response){
        ExcelWriter writer = null;
        ServletOutputStream out=null;
        try {
            writer = activityService.exportExcel();
            //response为HttpServletResponse对象
            response.setContentType("application/vnd.ms-excel;charset=utf-8");
            //test.xls是弹出下载对话框的文件名，不能为中文，中文请自行编码
            response.setHeader("Content-Disposition","attachment;filename=test.xls");
            out=response.getOutputStream();

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            writer.flush(out, true);
            // 关闭writer，释放内存
            writer.close();
            //此处记得关闭输出Servlet流
            IoUtil.close(out);
        }

    }


}
