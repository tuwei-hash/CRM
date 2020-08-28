package com.bjpowernode.crm.workbench.dao;

import com.bjpowernode.crm.workbench.domain.Tran;

import java.util.List;
import java.util.Map;

public interface TranDao {

    int insertTran(Tran t);

    Tran selectById(String id);

    List<Tran> selectHistoryListByTranId(String tranId);

    int updateStage(Tran t);

    List<String> selectStageNameList();

    int selectMaxCount();

    List<Map<String, Object>> selectChartData();

}
