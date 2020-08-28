package com.bjpowernode.crm.workbench.service.impl;

import com.bjpowernode.crm.workbench.dao.CustomerDao;
import com.bjpowernode.crm.workbench.dao.TranDao;
import com.bjpowernode.crm.workbench.domain.Tran;
import com.bjpowernode.crm.workbench.service.TranService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TranServiceImpl implements TranService {

    @Autowired
    TranDao tranDao;

    @Autowired
    CustomerDao customerDao;

    @Override
    public void saveTranForm(Tran t, String customerName) {

        String id = customerDao.selectIdByName(customerName);

        t.setCustomerId(id);

        tranDao.insertTran(t);

    }

    @Override
    public Tran getTranById(String id) {

        return tranDao.selectById(id);

    }

    @Override
    public List<Tran> getHistoryListByTranId(String tranId) {

        return tranDao.selectHistoryListByTranId(tranId);

    }

    @Override
    public void updateStage(Tran t) {

        tranDao.updateStage(t);

    }

    @Override
    public Map<String, Object> getChartData() {

        HashMap<String, Object> map = new HashMap<>();

        List<String> stageNameList = tranDao.selectStageNameList();

        int max = tranDao.selectMaxCount();

        List<Map<String,Object>> dataList = tranDao.selectChartData();

        map.put("stageNameList",stageNameList);
        map.put("max",max);
        map.put("dataList",dataList);

        return map;

    }

}
