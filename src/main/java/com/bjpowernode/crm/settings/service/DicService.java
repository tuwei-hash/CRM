package com.bjpowernode.crm.settings.service;

import com.bjpowernode.crm.settings.domain.DicType;
import com.bjpowernode.crm.settings.domain.DicValue;

import java.util.List;
import java.util.Map;

public interface DicService {

    List<DicType> getDicTypeList();

    List<DicValue> getDicValueList();

    boolean checkTypeCode(String code);

    int saveType(DicType dicType);

    DicType selectByCode(String code);

    int editType(DicType dicType);

    void deleteType(String[] codes);

    void saveValue(DicValue dicValue);

    DicValue toEditValue(String id);

    void editValue(DicValue dicValue);

    void deleteValueByIds(String[] ids);

    Map<String,List<DicValue>> getValueListByCode();

}
