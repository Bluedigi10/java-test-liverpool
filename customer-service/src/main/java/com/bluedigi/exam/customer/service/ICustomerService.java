package com.bluedigi.exam.customer.service;

import com.bluedigi.exam.customer.entity.CustomerEntity;

public interface ICustomerService {
    Integer getAllCustomers();
    Integer getCustomerById(Integer id);
    Integer createCustomer();
    Integer updateCustomer(Integer id, CustomerEntity customer);
}