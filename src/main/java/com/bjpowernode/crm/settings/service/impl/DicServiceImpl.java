package com.bjpowernode.crm.settings.service.impl;

import com.bjpowernode.crm.settings.dao.DicTypeDao;
import com.bjpowernode.crm.settings.dao.DicValueDao;
import com.bjpowernode.crm.settings.domain.DicType;
import com.bjpowernode.crm.settings.domain.DicValue;
import com.bjpowernode.crm.settings.service.DicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class DicServiceImpl implements DicService {

    @Autowired
    private DicTypeDao dicTypeDao;

    @Autowired
    private DicValueDao dicValueDao;

    @Override
    public List<DicType> getDicTypeList() {

        List<DicType> dtList=dicTypeDao.getDicTypeList();

        return dtList;
    }

    @Override
    public List<DicValue> getDicValueList() {

        List<DicValue> dvList=dicValueDao.getDicValueList();

        return dvList;
    }

    @Override
    public boolean checkTypeCode(String code) {

        int count=dicTypeDao.checkTypeCode(code);

        boolean flag = false;
        if (count>0){
            flag = true;
        }
        return flag;
    }

    @Override
    public int saveType(DicType dicType) {

       int row=dicTypeDao.saveType(dicType);

        return row;
    }

    @Override
    public DicType selectByCode(String code) {

        DicType dicType=dicTypeDao.selectByCode(code);

        return dicType;
    }

    @Override
    public int editType(DicType dicType) {

        int row=dicTypeDao.editType(dicType);
        return row;
    }

    @Override
    public void deleteType(String[] codes) {

        dicValueDao.deleteValueByCode(codes);
        dicTypeDao.deleteTypeByCode(codes);
    }

    @Override
    public void saveValue(DicValue dicValue) {

        dicValue.setId(UUID.randomUUID().toString().replaceAll("-",""));

        dicValueDao.insert(dicValue);

    }

    @Override
    public DicValue toEditValue(String id) {

        DicValue dicValue=dicValueDao.selectValueById(id);

        return dicValue;

    }

    @Override
    public void editValue(DicValue dicValue) {

        dicValueDao.updateValue(dicValue);

    }

    @Override
    public void deleteValueByIds(String[] ids) {

        dicValueDao.deleteValueByIds(ids);

    }

    @Override
    public Map<String, List<DicValue>> getValueListByCode() {

        Map<String,List<DicValue>> map = new HashMap<>();

        List<DicType> dtList = dicTypeDao.getDicTypeList();

        for (DicType dt :dtList) {

            String code = dt.getCode();

            List<DicValue> dvList = dicValueDao.getDicValueListByCode(code);

            map.put(code+"List",dvList);

        }

        return map;

    }

}
