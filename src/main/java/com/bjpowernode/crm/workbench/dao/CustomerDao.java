package com.bjpowernode.crm.workbench.dao;

import com.bjpowernode.crm.workbench.domain.Customer;

import java.util.List;

public interface CustomerDao {

    Customer selectCustomerByName(String name);

    int insertCustomer(Customer customer);

    List<Customer> selectCustomerListByName(String name);

    String selectIdByName(String customerName);

}
