package com.bjpowernode.crm.workbench.controller;

import com.bjpowernode.crm.base.Exception.CrmException;
import com.bjpowernode.crm.base.bean.ResultVo;
import com.bjpowernode.crm.base.util.CommonUtil;
import com.bjpowernode.crm.settings.bean.User;
import com.bjpowernode.crm.workbench.bean.*;
import com.bjpowernode.crm.workbench.service.ClueService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
public class ClueController {
    @Autowired
    private ClueService clueService;
    @RequestMapping("/workbench/clue/list")
    public PageInfo<Clue> list(int page, int pageSize,Clue clue){
        List<Clue> clues = clueService.list(page,pageSize,clue);
        PageInfo<Clue> cluePageInfo = new PageInfo<>(clues);
        return  cluePageInfo;
    }
    //保存或更新线索
    @RequestMapping("/workbench/clue/saveOrUpdate")
    @ResponseBody
    public ResultVo saveAndUpdate(Clue clue, HttpSession session){
        ResultVo resultVo =new ResultVo();
        //判断是创建还是修改
        try {
            User currentUser = CommonUtil.getCurrentUser(session);
            resultVo = clueService.saveOrUpate(clue,currentUser);
        } catch (CrmException e) {
            resultVo.setMessage(e.getMessage());
        }
        return resultVo;
    }
    //查询单个线索
    @RequestMapping("/workbench/clue/queryClue")
    @ResponseBody
    public Clue queryClue(String id){

        Clue clue =  clueService.queryClue(id);
        return clue;
    }
    //删除线索
    @RequestMapping("/workbench/clue/deleteClue")
    @ResponseBody
    public ResultVo deleteClue(String ids){
        ResultVo resultVo =new ResultVo();

        try {
            clueService.deleteBatch(ids);
            resultVo.setOk(true);
            resultVo.setMessage("删除线索成功！");
        } catch (CrmException e) {
            resultVo.setMessage(e.getMessage());
        }
        return resultVo;
    }
    //查询线索及备注
    @RequestMapping("/workbench/clue/queryDetail")
    @ResponseBody
    public Clue queryDetail(String id ){
        //id是联系人的id
        Clue clue =clueService.queryDetail(id);

        return clue;
    }
    //保存备注
    @RequestMapping("/workbench/clue/saveRemark")
    @ResponseBody
    public ResultVo saveRemark(ClueRemark clueRemark, HttpSession session){
        ResultVo resultVo =new ResultVo();
        User currentUser = CommonUtil.getCurrentUser(session);
        try {
            clueService.saveRemark(clueRemark,currentUser);
            resultVo.setOk(true);
            resultVo.setMessage("保存线索备注成功！");
        } catch (CrmException e) {
            resultVo.setMessage(e.getMessage());
        }
        return resultVo;
    }
    //更新备注
    @RequestMapping("/workbench/clue/updateRemark")
    @ResponseBody
    public ResultVo updateRemark(ClueRemark clueRemark,HttpSession session){
        ResultVo resultVo =new ResultVo();
        User currentUser = CommonUtil.getCurrentUser(session);
        try {
            clueService.updateRemark(clueRemark,currentUser);
            resultVo.setOk(true);
            resultVo.setMessage("更新线索备注成功！");
        } catch (CrmException e) {
            resultVo.setMessage(e.getMessage());
        }
        return resultVo;
    }
    //删除备注
    @RequestMapping("/workbench/clue/deleteRemark")
    @ResponseBody
    public ResultVo deleteRemark(String remarkId){
        ResultVo resultVo =new ResultVo();

        try {
            clueService.deleteRemark(remarkId);
            resultVo.setOk(true);
            resultVo.setMessage("删除线索备注成功！");
        } catch (CrmException e) {
            resultVo.setMessage(e.getMessage());
        }
        return resultVo;
    }
    //查询未关联的市场活动
    @RequestMapping("/workbench/clue/queryActivities")
    public List<Activity> queryActivities(String id,String name){
        List<Activity> activities=clueService.queryActivities(id,name);
        return activities;
    }
    //关联市场活动及线索
    @RequestMapping("/workbench/clue/saveRelation")
    public ResultVo bind(String id,String ids){
        ResultVo resultVo = new ResultVo();
        try {
            List<Activity> activities = clueService.bind(id,ids);
            resultVo.setMessage("关联市场活动成功！");
            resultVo.setOk(true);
            resultVo.setList(activities);
        } catch (CrmException e) {
            resultVo.setMessage(e.getMessage());
        }

        return resultVo;
    }
    //接触关联
    @RequestMapping("/workbench/clue/unbind")
    public ResultVo unbind(ClueActivityRelation clueActivityRelation){
        ResultVo resultVo = new ResultVo();
        try {
            clueService.unbind(clueActivityRelation);
            resultVo.setMessage("解除绑定成功！");
            resultVo.setOk(true);
        } catch (CrmException e) {
            resultVo.setMessage(e.getMessage());
        }

        return resultVo;
    }
    //查询已绑定的市场活动
    @RequestMapping("/workbench/clue/queryBindActivities")
    public List<Activity> queryBindActivities(String id,String name){
       List<Activity> activities = clueService.queryBindActivities(id,name);
        return activities;
    }
    //线索转换
    @RequestMapping("/workbench/clue/convert")
    public ResultVo convert(String isCreateTransaction,Tran tran,String clueId,HttpSession session ){
        ResultVo resultVo = new ResultVo();
        try {
            User currentUser = CommonUtil.getCurrentUser(session);
            clueService.convert(isCreateTransaction,tran,clueId,currentUser);
            resultVo.setOk(true);
            resultVo.setMessage("线索转换完成！");
        } catch (CrmException e) {
            resultVo.setMessage(e.getMessage());
        }
        return  resultVo;

    }

}
