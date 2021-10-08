package com.bjpowernode.crm.base.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.Enumeration;

/*抓门用来跳转页面得控制器*/
@Controller
public class ViewController {

    @RequestMapping({"/toView/{firstView}/{secondView}/{thirdView}","/toView/{firstView}/{secondView}","/toView/{firstView}/{secondView}/{thirdView}/{fourthView}"} )
    public String toMainIndex(@PathVariable(value = "firstView",required = false) String firstView,
                              @PathVariable(value = "secondView",required = false) String secondView,
                              @PathVariable(value = "thirdView",required = false) String thirdView,
                              @PathVariable(value = "fourthView",required = false) String fourthView,
                              HttpServletRequest request){
        Enumeration<String> parameterNames = request.getParameterNames();
        //遍历request域对象中所有的参数
        while(parameterNames.hasMoreElements()){
            //参数名
            String parameterName = parameterNames.nextElement();
            //通过属性名获取值
            String parameterValue = request.getParameter(parameterName);
            //放入
            request.setAttribute(parameterName, parameterValue);
        }
        if(fourthView != null){
            return firstView+ File.separator + secondView + File.separator + thirdView + File.separator + fourthView   ;
        }
        if(thirdView != null){
            return firstView+ File.separator + secondView + File.separator + thirdView;
        }

        return firstView+ File.separator + secondView ;
    }

    /*@RequestMapping("/toView/{firstView}/{secondView}/{thirdView}" )
    public String toMainIndex(@PathVariable(value = "firstView",required = false) String firstView) {
        if(thirdView != null){
            return firstView+ File.separator + secondView + File.separator + thirdView;
        }


        return firstView+ File.separator + secondView ;
    }*/

}
