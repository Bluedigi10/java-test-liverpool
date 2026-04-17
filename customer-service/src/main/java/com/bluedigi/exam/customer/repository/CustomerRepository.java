package com.bluedigi.exam.customer.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.bluedigi.exam.customer.entity.CustomerEntity;

@Repository
public interface CustomerRepository extends CrudRepository<CustomerEntity, Long> {

}