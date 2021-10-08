package com.bjpowernode.crm.workbench.controller;

import com.bjpowernode.crm.base.Exception.CrmException;
import com.bjpowernode.crm.base.bean.ResultVo;
import com.bjpowernode.crm.base.util.CommonUtil;
import com.bjpowernode.crm.settings.bean.User;
import com.bjpowernode.crm.settings.service.UserService;
import com.bjpowernode.crm.workbench.bean.Contact;
import com.bjpowernode.crm.workbench.bean.ContactRemark;
import com.bjpowernode.crm.workbench.service.ContactService;

import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class ContactController {

    @Autowired
    private UserService userService;
    @Autowired
    private ContactService contactService;

    @RequestMapping("/workbench/contact/list")
    @ResponseBody
    public PageInfo list(int page,int pageSize,Contact contact){

        //查询出每页的联系人集合
        List<Contact> contacts = contactService.selectAll(page,pageSize,contact);
        PageInfo<Contact> pageInfo = new PageInfo<>(contacts);
        return pageInfo;
    }
    //保存或更新联系人
    @RequestMapping("/workbench/contact/saveOrUpdate")
    @ResponseBody
    public ResultVo saveAndUpdate(Contact contact, HttpSession session){
        ResultVo resultVo =new ResultVo();
        //判断是创建还是修改
        try {
            User currentUser = CommonUtil.getCurrentUser(session);
            resultVo =contactService.saveOrUpate(contact,currentUser);
        } catch (CrmException e) {
            resultVo.setMessage(e.getMessage());
        }
        return resultVo;
    }
    //查询单个联系人
    @RequestMapping("/workbench/contact/queryContact")
    @ResponseBody
    public Contact queryContact(String id){

        Contact contact =  contactService.queryContact(id);
        return contact;
    }
    //删除联系人
    @RequestMapping("/workbench/contact/deleteContact")
    @ResponseBody
    public ResultVo deleteContact(String ids){
        ResultVo resultVo =new ResultVo();

        try {
            contactService.deleteBatch(ids);
            resultVo.setOk(true);
            resultVo.setMessage("删除联系人成功！");
        } catch (CrmException e) {
            resultVo.setMessage(e.getMessage());
        }
        return resultVo;
    }
    //查询客户及备注
    @RequestMapping("/workbench/contact/queryDetail")
    @ResponseBody
    public Contact queryDetail(String id ){
        //id是联系人的id
        Contact contact =contactService.queryDetail(id);

        return contact;
    }
    //保存备注
    @RequestMapping("/workbench/contact/saveRemark")
    @ResponseBody
    public ResultVo saveRemark(ContactRemark contactRemark, HttpSession session){
        ResultVo resultVo =new ResultVo();
        User currentUser = CommonUtil.getCurrentUser(session);
        try {
            contactService.saveRemark(contactRemark,currentUser);
            resultVo.setOk(true);
            resultVo.setMessage("保存联系人备注成功！");
        } catch (CrmException e) {
            resultVo.setMessage(e.getMessage());
        }
        return resultVo;
    }
    //更新备注
    @RequestMapping("/workbench/contact/updateRemark")
    @ResponseBody
    public ResultVo updateRemark(ContactRemark contactRemark,HttpSession session){
        ResultVo resultVo =new ResultVo();
        User currentUser = CommonUtil.getCurrentUser(session);
        try {
            contactService.updateRemark(contactRemark,currentUser);
            resultVo.setOk(true);
            resultVo.setMessage("更新联系人备注成功！");
        } catch (CrmException e) {
            resultVo.setMessage(e.getMessage());
        }
        return resultVo;
    }
    //删除备注
    @RequestMapping("/workbench/contact/deleteRemark")
    @ResponseBody
    public ResultVo deleteRemark(String remarkId){
        ResultVo resultVo =new ResultVo();

        try {
            contactService.deleteRemark(remarkId);
            resultVo.setOk(true);
            resultVo.setMessage("删除客户备注成功！");
        } catch (CrmException e) {
            resultVo.setMessage(e.getMessage());
        }
        return resultVo;
    }
}
