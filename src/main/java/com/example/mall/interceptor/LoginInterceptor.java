package com.example.mall.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginInterceptor implements HandlerInterceptor {

    /**
     * 检测全局session对象中是否有uid数据，如果有则放行，没有则重定向到登陆页面
     * @param request
     * @param response
     * @param handler
     * @return 返回为true，放行当前请求
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //登陆检查逻辑
        HttpSession session = request.getSession();
        Object loginUser = session.getAttribute("uid");
        if(loginUser != null) {
            return true;
        }
        //没有登陆重定向
        response.sendRedirect("/web/login.html");
        //结束后续的调用
        return false;
    }

}
