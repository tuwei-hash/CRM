package com.bjpowernode.crm.workbench.service;

import com.bjpowernode.crm.workbench.domain.Tran;

import java.util.List;
import java.util.Map;

public interface TranService {

    void saveTranForm(Tran t, String customerName);

    Tran getTranById(String id);

    List<Tran> getHistoryListByTranId(String tranId);

    void updateStage(Tran t);

    Map<String, Object> getChartData();

}
