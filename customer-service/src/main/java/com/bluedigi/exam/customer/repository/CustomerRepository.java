package com.bluedigi.exam.customer.repository;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import com.bluedigi.exam.customer.entity.CustomerEntity;

@Repository
public interface CustomerRepository extends ListCrudRepository<CustomerEntity, Long> {
    boolean existsByEmail(String email);
}