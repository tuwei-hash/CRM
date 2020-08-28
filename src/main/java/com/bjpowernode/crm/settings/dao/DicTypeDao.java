package com.bjpowernode.crm.settings.dao;

import com.bjpowernode.crm.settings.domain.DicType;

import java.util.List;

public interface DicTypeDao {

    List<DicType> getDicTypeList();

    int checkTypeCode(String code);

    int saveType(DicType dicType);

    DicType selectByCode(String code);

    int editType(DicType dicType);

    int deleteTypeByCode(String[] codes);

}
