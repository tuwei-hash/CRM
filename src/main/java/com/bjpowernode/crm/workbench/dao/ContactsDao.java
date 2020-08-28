package com.bjpowernode.crm.workbench.dao;

import com.bjpowernode.crm.workbench.domain.Contacts;

import java.util.List;

public interface ContactsDao {

    void insertContacts(Contacts con);

    List<Contacts> selectContactsByFullName(String cName);
}
