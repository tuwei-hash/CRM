package com.bjpowernode.crm.workbench.dao;

import com.bjpowernode.crm.workbench.domain.Activity;
import com.bjpowernode.crm.workbench.domain.ClueActivityRelation;

import java.util.List;
import java.util.Map;

public interface ClueActivityRelationDao {

    List<Activity> selectActivityByNameNotBindClue(Map<String, String> map);

    void insertRelationList(List<ClueActivityRelation> carList);

    List<ClueActivityRelation> selectRelationByClueId(String clueId);

    void deleteRelationById(String id);
}
