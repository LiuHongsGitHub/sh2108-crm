package com.bjpowernode.crm.workbench.controller;


import com.bjpowernode.crm.base.Exception.CrmException;
import com.bjpowernode.crm.base.bean.ResultVo;
import com.bjpowernode.crm.base.util.CommonUtil;
import com.bjpowernode.crm.workbench.bean.ActivityRemark;
import com.bjpowernode.crm.workbench.bean.Customer;
import com.bjpowernode.crm.settings.bean.User;
import com.bjpowernode.crm.workbench.bean.CustomerRemark;
import com.bjpowernode.crm.workbench.service.CustomerService;
import com.bjpowernode.crm.settings.service.UserService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class CustomerController {
    @Autowired
    private CustomerService customerService;
    @Autowired
    UserService userService;

    //使用分页插件进行分页
    @RequestMapping("/workbench/customer/list")
    @ResponseBody
    public PageInfo activityList(int page, int pageSize, Customer customer){


        List<Customer> customers = customerService.list(page, pageSize, customer);

        PageInfo<Customer> PageInfo = new PageInfo<>(customers);

        return PageInfo;
    }
    //查询全部所有者的信息
    @RequestMapping("/workbench/customer/queryUsers")
    @ResponseBody
    public List<User> queryUsers(){
        List<User> users = userService.queryAllUser();
        return users;
    }
    //保存或更新市场活动
    @RequestMapping("/workbench/customer/saveOrUpdate")
    @ResponseBody
    public ResultVo saveAndUpdate(Customer customer, HttpSession session){
        ResultVo resultVo =new ResultVo();
        //判断是创建还是修改
        try {
            User currentUser = CommonUtil.getCurrentUser(session);
            resultVo =customerService.saveOrUpate(customer,currentUser);

        } catch (CrmException e) {
            resultVo.setMessage(e.getMessage());
        }
        return resultVo;
    }
    //查询单个客户
    @RequestMapping("/workbench/customer/queryCustomer")
    @ResponseBody
    public Customer queryCustomer(String id){

        Customer customers =  customerService.queryCustomer(id);
        return customers;
    }
    //删除客户
    @RequestMapping("/workbench/customer/deleteCustomer")
    @ResponseBody
    public ResultVo deleteCustomer(String ids){
        ResultVo resultVo =new ResultVo();

        try {
            customerService.deleteBatch(ids);
            resultVo.setOk(true);
            resultVo.setMessage("删除客户成功！");
        } catch (CrmException e) {
            resultVo.setMessage(e.getMessage());
        }
        return resultVo;
    }
    //查询客户及备注
    @RequestMapping("/workbench/customer/queryDetail")
    @ResponseBody
    public Customer queryDetail(String id ){
        Customer customer =customerService.queryDetail(id);

        return customer;
    }
    //保存备注
    @RequestMapping("/workbench/customer/saveRemark")
    @ResponseBody
    public ResultVo saveRemark(CustomerRemark customerRemark, HttpSession session){
        ResultVo resultVo =new ResultVo();
        User currentUser = CommonUtil.getCurrentUser(session);
        try {
            customerService.saveRemark(customerRemark,currentUser);
            resultVo.setOk(true);
            resultVo.setMessage("保存客户备注成功！");
        } catch (CrmException e) {
            resultVo.setMessage(e.getMessage());
        }
        return resultVo;
    }
    //更新备注
    @RequestMapping("/workbench/customer/updateRemark")
    @ResponseBody
    public ResultVo updateRemark(CustomerRemark customerRemark,HttpSession session){
        ResultVo resultVo =new ResultVo();
        User currentUser = CommonUtil.getCurrentUser(session);
        try {
            customerService.updateRemark(customerRemark,currentUser);
            resultVo.setOk(true);
            resultVo.setMessage("更新客户备注成功！");
        } catch (CrmException e) {
            resultVo.setMessage(e.getMessage());
        }
        return resultVo;
    }
    //删除备注
    @RequestMapping("/workbench/customer/deleteRemark")
    @ResponseBody
    public ResultVo deleteRemark(String remarkId){
        ResultVo resultVo =new ResultVo();

        try {
            customerService.deleteRemark(remarkId);
            resultVo.setOk(true);
            resultVo.setMessage("删除客户备注成功！");
        } catch (CrmException e) {
            resultVo.setMessage(e.getMessage());
        }
        return resultVo;
    }
}
