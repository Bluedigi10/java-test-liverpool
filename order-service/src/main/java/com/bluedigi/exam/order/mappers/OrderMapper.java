package com.bluedigi.exam.order.mappers;

import com.bluedigi.exam.order.dto.OrderRequestDTO;
import com.bluedigi.exam.order.dto.OrderResponseDTO;
import com.bluedigi.exam.order.entity.OrderEntity;

public class OrderMapper {
    
    public static OrderEntity toEntity(OrderRequestDTO request) {
        OrderEntity entity = new OrderEntity();

        entity.setProductCode(request.getProductCode());
        entity.setCustomerId(request.getCustomerId());
        entity.setQuantity(request.getQuantity());
        entity.setPrice(request.getPrice());


        return entity;
    }

    public static OrderEntity toEntity(OrderEntity existing, OrderRequestDTO request) {

        existing.setProductCode(request.getProductCode());
        existing.setCustomerId(request.getCustomerId());
        existing.setQuantity(request.getQuantity());
        existing.setPrice(request.getPrice());

        return existing;
    }

    public static OrderResponseDTO toResponseDTO(OrderEntity entity) {
        OrderResponseDTO response = new OrderResponseDTO();

        response.setOrderId(entity.getOrderId());
        response.setProductCode(entity.getProductCode());
        response.setCustomerId(entity.getCustomerId());
        response.setQuantity(entity.getQuantity());
        response.setPrice(entity.getPrice());
        response.setCreatedAt(entity.getCreatedAt());
        response.setUpdatedAt(entity.getUpdatedAt());

        return response;
    }
}
