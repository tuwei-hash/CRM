package com.bjpowernode.crm.settings.dao;

import com.bjpowernode.crm.settings.domain.DicValue;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DicValueDao {

    List<DicValue> getDicValueList();

    int deleteValueByCode(String[] codes);

    int insert(DicValue dicValue);

    DicValue selectValueById(@Param("id") String id);

    int updateValue(DicValue dicValue);

    int deleteValueByIds(String[] ids);

    List<DicValue> getDicValueListByCode(String code);

}
