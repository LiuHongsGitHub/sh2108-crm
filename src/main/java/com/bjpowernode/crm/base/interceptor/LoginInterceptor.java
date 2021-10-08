package com.bjpowernode.crm.base.interceptor;

import com.bjpowernode.crm.settings.bean.User;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginInterceptor implements HandlerInterceptor {
    //定义自己的拦截器，实现框架中某个接口
    //自定义的拦截器注入到ioc中
    //配置拦截器具体过滤路径
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        /*String uri = request.getRequestURI();
        StringBuffer requestURL = request.getRequestURL();
        if (uri.contains("code")){
            return true;
        }*/
        //判断是否登录

        User user = (User) request.getSession().getAttribute("user");
        if (user ==null){
            //用户没有登录重定向至登录页面
            response.sendRedirect("/crm/login.jsp");
        }

        return true;
    }
}
