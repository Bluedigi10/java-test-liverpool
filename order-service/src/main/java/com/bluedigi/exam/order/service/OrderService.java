package com.bluedigi.exam.order.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.bluedigi.exam.order.dto.OrderRequestDTO;
import com.bluedigi.exam.order.dto.OrderResponseDTO;
import com.bluedigi.exam.order.entity.OrderEntity;
import com.bluedigi.exam.order.exception.OrderException;
import com.bluedigi.exam.order.mappers.OrderMapper;
import com.bluedigi.exam.order.repository.OrderRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class OrderService{
    private final OrderRepository orderRepository;

    public List<OrderResponseDTO> getAllOrders() {
        return orderRepository.findAll().stream().map(entity -> OrderMapper.toResponseDTO(entity)).toList();
    }

    public OrderResponseDTO getOrderById(Long id) {
        return OrderMapper.toResponseDTO(getOrderByIdEntity(id));
    }

    public OrderResponseDTO createOrder(OrderRequestDTO customer) {
        return OrderMapper.toResponseDTO(orderRepository.save(OrderMapper.toEntity(customer)));
    }

    public OrderResponseDTO updateOrder(Long id, OrderRequestDTO customer) {
        OrderEntity existingOrder = getOrderByIdEntity(id);
        // Update the existing order with the new data
        return OrderMapper.toResponseDTO(orderRepository.save(OrderMapper.toEntity(existingOrder, customer)));
    }

    public void deleteOrder(Long id) {
        getOrderByIdEntity(id);
        orderRepository.deleteById(id);
    }

    private OrderEntity getOrderByIdEntity(Long id) {
        return orderRepository.findById(id).orElseThrow(() -> new OrderException(HttpStatus.NOT_FOUND, "Order with ID: " + id + " not found"));
    }
    
    
}
