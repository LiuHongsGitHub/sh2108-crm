package com.bjpowernode.crm.workbench.controller;

import com.bjpowernode.crm.base.util.CommonUtil;
import com.bjpowernode.crm.settings.bean.User;
import com.bjpowernode.crm.workbench.bean.Tran;
import com.bjpowernode.crm.workbench.service.TranService;

import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@RestController
public class TranController {
    @Autowired
    private TranService tranService;

    @RequestMapping("/workbench/transaction/queryCustomerName")
    public List<String> queryCustomerName(String customerName){
        List<String> customerNames = tranService.queryCustomerName(customerName);

        return customerNames;
    }
    @RequestMapping("/workbench/transaction/list")
    public PageInfo list(Tran tran,int page, int pageSize){
       List<Tran> trans = tranService.list(tran,page,pageSize);
        PageInfo<Tran> pageInfo = new PageInfo<>(trans);

        return pageInfo;
    }
    //根据阶段获取可能性
    @RequestMapping("/workbench/tran/queryPossibility")
    public  String queryPossibility(String stage, HttpSession session){
        //取得servletContext
        ServletContext servletContext = session.getServletContext();
        Map<String,String> stage2Possibility = (Map)servletContext.getAttribute("stage2Possibility");
        String possibility = stage2Possibility.get(stage);
        return possibility;
    }
    //详情页查询所处状态的图标表示/crm
    @RequestMapping("/workbench/transaction/queryStage")
    public Map<String, Object> queryStage(Integer index,String id ,HttpSession session){
        ServletContext servletContext = session.getServletContext();
        Map<String,String> stage2Possibility = (Map<String,String>)servletContext.getAttribute("stage2Possibility");
        User currentUser = CommonUtil.getCurrentUser(session);
        Map<String, Object> stageVos = tranService.queryStage(index,id,stage2Possibility,currentUser);

        return stageVos;
    }



}
