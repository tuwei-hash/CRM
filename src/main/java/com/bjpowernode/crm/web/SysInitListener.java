package com.bjpowernode.crm.web;

import com.bjpowernode.crm.settings.domain.DicValue;
import com.bjpowernode.crm.settings.service.DicService;
import com.mysql.jdbc.AbandonedConnectionCleanupThread;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.*;

public class SysInitListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {

        ServletContext application = servletContextEvent.getServletContext();

        DicService dicService = WebApplicationContextUtils.getWebApplicationContext(application).getBean(DicService.class);

        Map<String,List<DicValue>> map = dicService.getValueListByCode();

        Set<String> set = map.keySet();

        for (String code : set){

            application.setAttribute(code,map.get(code));

        }


        HashMap<String, String> pMap = new HashMap<>();

        ResourceBundle rb = ResourceBundle.getBundle("properties/Stage2Possibility");

        Enumeration<String> keys = rb.getKeys();

        while (keys.hasMoreElements()){

            String key = keys.nextElement();

            String value = rb.getString(key);

            pMap.put(key,value);

        }

        application.setAttribute("pMap", pMap);

    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }

}
