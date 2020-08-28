
package com.bjpowernode.crm.workbench.dao;

import com.bjpowernode.crm.workbench.domain.Activity;
import java.util.List;
import java.util.Map;

public interface ActivityDao {
    int insertActivity(Activity var1);

    List<Activity> selectByCondition(Map var1);

    int selectCountByCondition(Map var1);

    Activity selectActivityById(String var1);

    int updateActivity(Activity var1);

    int deleteActivityByIds(String[] var1);

    List<Activity> selectAllActivity();

    List<Activity> selectActivityByIds(String[] var1);

    int insertActivityList(List<Activity> var1);

    Activity selectActivityByIdForOwner(String var1);

    List<Activity> selectActivityByClueId(String var1);

    List<Activity> selectActivityByName(String var1);
}

