package com.bjpowernode.crm.workbench.service.impl;

import com.bjpowernode.crm.workbench.dao.ContactsDao;
import com.bjpowernode.crm.workbench.dao.CustomerDao;
import com.bjpowernode.crm.workbench.domain.Contacts;
import com.bjpowernode.crm.workbench.domain.Customer;
import com.bjpowernode.crm.workbench.service.ContactsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContactsServiceImpl implements ContactsService {

    @Autowired
    ContactsDao contactsDao;

    @Autowired
    CustomerDao customerDao;

    @Override
    public List<Contacts> getContactsListByFullName(String cName) {

        return contactsDao.selectContactsByFullName(cName);

    }

    @Override
    public List<Customer> getCustomerNameListByName(String name) {

        return customerDao.selectCustomerListByName(name);

    }

}
