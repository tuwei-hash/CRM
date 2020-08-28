package com.bjpowernode.crm.interceptor;

import com.bjpowernode.crm.exception.InterceptorException;
import com.bjpowernode.crm.settings.domain.User;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        /*

            判断有没有登录过的依据：
            从session对象中取得user对象
            如果有user对象，则说明登录过，将请求放行到目标资源
            如果没有user对象，则说明没有登录过，返回到登录页

         */

        User user = (User) request.getSession().getAttribute("user");

        if(user==null){

            //没有登录过，抛出自定义异常，由异常处理器帮我们处理
            throw new InterceptorException();

        }

        //将请求放行
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
