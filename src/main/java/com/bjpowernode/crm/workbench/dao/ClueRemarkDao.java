package com.bjpowernode.crm.workbench.dao;

import com.bjpowernode.crm.workbench.domain.ClueRemark;

import java.util.List;

public interface ClueRemarkDao {

    List<ClueRemark> selectClueRemarkByClueId(String clueId);

    int deleteClueRemarkById(String id);

}
