package com.bjpowernode.crm.workbench.service.impl;

import com.bjpowernode.crm.settings.dao.UserDao;
import com.bjpowernode.crm.utils.PaginationVo;
import com.bjpowernode.crm.workbench.dao.ActivityDao;
import com.bjpowernode.crm.workbench.domain.Activity;
import com.bjpowernode.crm.workbench.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ActivityServiceImpl implements ActivityService {

    @Autowired
    ActivityDao activityDao;

    @Autowired
    UserDao userDao;

    @Override
    public void saveActivity(Activity activity) {

        activityDao.insertActivity(activity);
    }

    @Override
    public PaginationVo<Activity> getPageList(Map map) {

        List<Activity> activityList=activityDao.selectByCondition(map);

        int total=activityDao.selectCountByCondition(map);

        PaginationVo<Activity> vo=new PaginationVo<>(activityList,total);

        return vo;

    }

    @Override
    public Activity getActivityById(String id) {

        return activityDao.selectActivityById(id);

    }

    @Override
    public void updateActivity(Activity a) {

        activityDao.updateActivity(a);

    }

    @Override
    public void deleteActivityByIds(String[] ids) {

        activityDao.deleteActivityByIds(ids);

    }

    @Override
    public List<Activity> getActivityList() {

        return activityDao.selectAllActivity();

    }

    @Override
    public List<Activity> getActivityByIds(String[] ids) {

        return activityDao.selectActivityByIds(ids);
    }

    @Override
    public void saveActivityList(List<Activity> aList) {

        activityDao.insertActivityList(aList);

    }

    @Override
    public Activity getActivityByIdForOwner(String id) {

        return activityDao.selectActivityByIdForOwner(id);

    }

}
