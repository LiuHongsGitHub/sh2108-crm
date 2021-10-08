package com.bjpowernode.crm.settings.controller;

import com.bjpowernode.crm.base.Exception.CrmEnum;
import com.bjpowernode.crm.base.Exception.CrmException;
import com.bjpowernode.crm.base.bean.ResultVo;
import com.bjpowernode.crm.base.util.FileUploadUtil;
import com.bjpowernode.crm.settings.bean.User;
import com.bjpowernode.crm.settings.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;


@Controller
public class UserController {
    @Autowired
    private UserService userService;


   //web层：1、接收客户端数据2、给客户端响应
    @RequestMapping("settings/user/login")
    @ResponseBody
    public ResultVo login(User user, String code, HttpSession session, HttpServletRequest request){

        //生成对象返回数据
        ResultVo<Object> rv = new ResultVo<>();

        //获取正确的验证码
        String correctCode = (String)session.getAttribute("correctCode");

        /*作比较放在业务层
        if ()*/
        try {
            //用户名，验证码等输入校验成功
            //获取ip
            String ip = request.getRemoteAddr();
            System.out.println(ip);
            User user1 = userService.login(user, code, correctCode, ip);

            //放入域对象
            session.setAttribute("user", user1);
            rv.setOk(true);
        } catch (CrmException e) {
            rv.setMessage(e.getMessage());
        }
        return rv;
    }

    //退出后台的首页
    @RequestMapping("/settings/user/logOut")
    public String logOut(HttpSession session){
        //清空session中的信息
        session.removeAttribute("user");
        //重定向
        return "redirect:/login.jsp";

    }
    //验证旧密码是否正确
    @RequestMapping("/settings/user/verifyOldPwd")
    @ResponseBody
    public ResultVo verifyOldPwd(String oldPwd,HttpSession session){
        ResultVo resultVo = new ResultVo();
        //登录校验成功之后就将用户名和密码放入session中
        User loginPwd = (User) session.getAttribute("user");
        try {
            userService.verify(loginPwd,oldPwd);
        } catch (CrmException e) {
            resultVo.setMessage(e.getMessage());
        }
        return resultVo;
    }

    //异步上传头像
    @RequestMapping("/settings/user/fileUpload")
    @ResponseBody
    public ResultVo fileUpload(MultipartFile photo,HttpServletRequest request){
       return FileUploadUtil.fileUpLoad(photo, request) ;
    }
    //更新用户头像及密码
    @RequestMapping("/settings/user/updateUser")
    @ResponseBody
    public ResultVo updateUser(User user,HttpSession session){
        ResultVo resultVo = new ResultVo();

        try {
            userService.updateUser(user);
            resultVo.setOk(true);
            resultVo.setMessage("更新用户成功");
            session.getAttribute("user");

        } catch (CrmException e) {
            resultVo.setMessage(e.getMessage());
        }
        return resultVo;
    }
}
