package com.bluedigi.exam.customer.mappers;

import com.bluedigi.exam.customer.dto.CustomerRequestDTO;
import com.bluedigi.exam.customer.dto.CustomerResponseDTO;
import com.bluedigi.exam.customer.entity.CustomerEntity;

public class CustomerMapper {
    
    public static CustomerEntity toEntity(CustomerRequestDTO request) {
        CustomerEntity entity = new CustomerEntity();

        entity.setFirstName(request.getFirstName());
        entity.setMiddleName(request.getMiddleName());
        entity.setPaternalLastName(request.getPaternalLastName());
        entity.setMaternalLastName(request.getMaternalLastName());
        entity.setEmail(request.getEmail());
        entity.setShippingAddress(request.getShippingAddress());

        return entity;
    }

    public static CustomerEntity toEntity(CustomerEntity existing, CustomerRequestDTO request) {

        existing.setFirstName(request.getFirstName());
        existing.setMiddleName(request.getMiddleName());
        existing.setPaternalLastName(request.getPaternalLastName());
        existing.setMaternalLastName(request.getMaternalLastName());
        existing.setEmail(request.getEmail());
        existing.setShippingAddress(request.getShippingAddress());

        return existing;
    }

    public static CustomerResponseDTO toResponseDTO(CustomerEntity entity) {
        CustomerResponseDTO response = new CustomerResponseDTO();

        response.setCustomerId(entity.getCustomerId());
        response.setFirstName(entity.getFirstName());
        response.setMiddleName(entity.getMiddleName());
        response.setPaternalLastName(entity.getPaternalLastName());
        response.setMaternalLastName(entity.getMaternalLastName());
        response.setEmail(entity.getEmail());
        response.setShippingAddress(entity.getShippingAddress());
        response.setCreatedAt(entity.getCreatedAt());
        response.setUpdatedAt(entity.getUpdatedAt());

        return response;
    }
}
