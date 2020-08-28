package com.bjpowernode.crm.settings.web.controller;

import com.bjpowernode.crm.exception.LoginException;
import com.bjpowernode.crm.settings.domain.User;
import com.bjpowernode.crm.settings.service.UserService;
import com.bjpowernode.crm.utils.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/settings/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/login.do")
    @ResponseBody
    public Map<String,Object> login(String loginAct, String loginPwd, String flag,
                                    HttpServletRequest request, HttpServletResponse response){

        loginPwd = MD5Util.getMD5(loginPwd);

        String ip = request.getRemoteAddr();

        Map<String,Object> map = new HashMap<>();

        try {

            User user = userService.login(loginAct,loginPwd,ip);

            request.getSession().setAttribute("user",user);

            if("a".equals(flag)){

                Cookie cookie1 = new Cookie("loginAct",loginAct);
                Cookie cookie2 = new Cookie("loginPwd",loginPwd);

                cookie1.setMaxAge(60*60*24*10);
                cookie2.setMaxAge(60*60*24*10);

                cookie1.setPath("/01_crm1");
                cookie2.setPath("/01_crm1");

                response.addCookie(cookie1);
                response.addCookie(cookie2);

            }
            map.put("success",true);

            return map;

        } catch (LoginException e) {

            e.printStackTrace();

            map.put("success",false);
            map.put("msg",e.getMessage());

            return map;
        }
    }

    @RequestMapping("/toLogin.do")
    public String toLogin(HttpServletRequest request){

        Cookie[] cookies = request.getCookies();

        if(cookies!=null && cookies.length>0){

            String loginAct = null;
            String loginPwd = null;

            for (Cookie cookie : cookies) {

                String name = cookie.getName();

                if("loginAct".equals(name)){

                    loginAct = cookie.getValue();

                    continue;
                }

                if("loginPwd".equals(name)){

                    loginPwd = cookie.getValue();
                }
            }

            if(loginAct!=null && loginPwd!=null){

                String ip = request.getRemoteAddr();

                try {

                    User user = userService.login(loginAct,loginPwd,ip);

                    request.getSession().setAttribute("user",user);

                    return "redirect:/workbench/toIndex.do";

                } catch (LoginException e) {
                    e.printStackTrace();

                    return "/login";
                }

            }else{

                return "/login";
            }
        }else{

            return "/login";
        }
    }

    @RequestMapping("/logout.do")
    public String logout(HttpServletRequest request,HttpServletResponse response){

        request.getSession().invalidate();

        Cookie cookie1 = new Cookie("loginAct",null);
        Cookie cookie2 = new Cookie("loginPwd",null);

        cookie1.setMaxAge(0);
        cookie2.setMaxAge(0);

        cookie1.setPath("/01_crm1");
        cookie2.setPath("/01_crm1");

        response.addCookie(cookie1);
        response.addCookie(cookie2);

        return "/login";
    }
}