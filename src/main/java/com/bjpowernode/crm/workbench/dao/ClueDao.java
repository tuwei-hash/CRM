package com.bjpowernode.crm.workbench.dao;

import com.bjpowernode.crm.workbench.domain.Clue;
import java.util.List;
import java.util.Map;

public interface ClueDao {
    List<Clue> selectAllByCondition(Map var1);

    int selectCountByCondition(Map var1);

    Clue selectClueById(String var1);

    int insertClue(Clue var1);

    int deleteClueById(String var1);

}