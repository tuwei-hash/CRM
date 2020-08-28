package com.bjpowernode.crm.workbench.service;

import com.bjpowernode.crm.workbench.domain.ActivityRemark;

import java.util.List;

public interface ActivityRemarkService {

    List<ActivityRemark> getRemarkListByAid(String aId);

    void updateRemark(ActivityRemark ar);

    void deleteRemarkById(String id);

    void saveRemark(ActivityRemark ar);

}
