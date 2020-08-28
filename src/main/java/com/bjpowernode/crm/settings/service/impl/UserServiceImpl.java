package com.bjpowernode.crm.settings.service.impl;

import com.bjpowernode.crm.exception.LoginException;
import com.bjpowernode.crm.settings.dao.UserDao;
import com.bjpowernode.crm.settings.domain.User;
import com.bjpowernode.crm.settings.service.UserService;
import com.bjpowernode.crm.utils.Const;
import com.bjpowernode.crm.utils.DateTimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public User login(String loginAct, String loginPwd, String ip) throws LoginException {

        Map<String,String> map = new HashMap<>();
        map.put("loginAct",loginAct);
        map.put("loginPwd",loginPwd);

        User user = userDao.login(map);

        if(user==null){

            throw new LoginException("账号密码错误");

        }

        String expireTime = user.getExpireTime();
        if(expireTime.compareTo(DateTimeUtil.getSysTime())<0){

            throw new LoginException("账号已失效");

        }

        String lockState = user.getLockState();
        if(Const.LOCK_STATE_CLOSE.equals(lockState)){

            throw new LoginException("账号已锁定");

        }

        String allowIps = user.getAllowIps();

        if(allowIps!=null){

            if(!allowIps.contains(ip)){

                throw new LoginException("ip地址受限");
            }
        }
        return user;
    }

    @Override
    public List<User> getUserList() {

        return userDao.selectAllUser();
    }

}














































