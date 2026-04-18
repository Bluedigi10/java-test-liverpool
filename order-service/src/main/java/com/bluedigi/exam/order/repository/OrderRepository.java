package com.bluedigi.exam.order.repository;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import com.bluedigi.exam.order.entity.OrderEntity;

@Repository
public interface OrderRepository extends ListCrudRepository<OrderEntity, Long> {

}