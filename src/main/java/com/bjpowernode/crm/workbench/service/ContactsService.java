package com.bjpowernode.crm.workbench.service;

import com.bjpowernode.crm.workbench.domain.Contacts;
import com.bjpowernode.crm.workbench.domain.Customer;

import java.util.List;

public interface ContactsService {

    List<Contacts> getContactsListByFullName(String cName);

    List<Customer> getCustomerNameListByName(String name);
}
