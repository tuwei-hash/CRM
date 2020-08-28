package com.bjpowernode.crm.workbench.service;

import com.bjpowernode.crm.utils.PaginationVo;
import com.bjpowernode.crm.workbench.domain.Activity;

import java.util.List;
import java.util.Map;

public interface ActivityService {

    void saveActivity(Activity activity);

    PaginationVo<Activity> getPageList(Map map);

    Activity getActivityById(String id);

    void updateActivity(Activity a);

    void deleteActivityByIds(String[] ids);

    List<Activity> getActivityList();

    List<Activity> getActivityByIds(String[] ids);

    void saveActivityList(List<Activity> aList);

    Activity getActivityByIdForOwner(String id);

}
