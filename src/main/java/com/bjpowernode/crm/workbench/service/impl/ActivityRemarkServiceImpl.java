package com.bjpowernode.crm.workbench.service.impl;

import com.bjpowernode.crm.workbench.dao.ActivityRemarkDao;
import com.bjpowernode.crm.workbench.domain.ActivityRemark;
import com.bjpowernode.crm.workbench.service.ActivityRemarkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ActivityRemarkServiceImpl implements ActivityRemarkService {

    @Autowired
    ActivityRemarkDao remarkDao;

    @Override
    public List<ActivityRemark> getRemarkListByAid(String aId) {

        return remarkDao.selectRemarkListByAid(aId) ;

    }

    @Override
    public void updateRemark(ActivityRemark ar) {

        remarkDao.updateRemark(ar);

    }

    @Override
    public void deleteRemarkById(String id) {

        remarkDao.deleteRemarkById(id);

    }

    @Override
    public void saveRemark(ActivityRemark ar) {

        remarkDao.insertRemark(ar);

    }

}
