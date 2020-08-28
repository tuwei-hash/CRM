package com.bjpowernode.crm.workbench.service;

import com.bjpowernode.crm.settings.domain.User;
import com.bjpowernode.crm.utils.PaginationVo;
import com.bjpowernode.crm.workbench.domain.Activity;
import com.bjpowernode.crm.workbench.domain.Clue;
import com.bjpowernode.crm.workbench.domain.Tran;

import java.util.List;
import java.util.Map;

public interface ClueService {

    List<User> getUserList();

    PaginationVo<Clue> getClueListByCondition(Map map);

    Clue getClueById(String id);

    List<Activity> getActivityListByClueId(String id);

    List<Activity> getActivityListByNameNotBindClue(Map map);

    void saveClueActivityRelationList(String clueId,String[] aId);

    void deleteClueActivityRelationById(String id);

    List<Activity> getActivityListByName(String aName);

    void convert(String clueId, Tran t, String flag, String createBy);

}
