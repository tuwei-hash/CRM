package com.bjpowernode.crm.workbench.dao;

import com.bjpowernode.crm.workbench.domain.ActivityRemark;
import java.util.List;

public interface ActivityRemarkDao {
    List<ActivityRemark> selectRemarkListByAid(String aId);

    int updateRemark(ActivityRemark var1);

    int deleteRemarkById(String var1);

    int insertRemark(ActivityRemark var1);
}
