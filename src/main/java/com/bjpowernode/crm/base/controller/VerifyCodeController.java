package com.bjpowernode.crm.base.controller;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.ShearCaptcha;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/*负责生产图片验证码*/
@Controller
public class VerifyCodeController {
    @RequestMapping("/getcode")
    public void getCode(HttpServletRequest request, HttpServletResponse response,HttpSession session) throws IOException {
        //定义图形验证码的长、宽、验证码字符数、干扰线宽度
        ShearCaptcha captcha = CaptchaUtil.createShearCaptcha(110, 50, 4, 4);
        captcha.write(response.getOutputStream());
        String correctCode  = captcha.getCode();
        session.setAttribute("correctCode",correctCode);
    }

}
