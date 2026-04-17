package com.bluedigi.exam.customer.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.bluedigi.exam.customer.dto.CustomerRequestDTO;
import com.bluedigi.exam.customer.dto.CustomerResponseDTO;
import com.bluedigi.exam.customer.entity.CustomerEntity;
import com.bluedigi.exam.customer.mappers.CustomerMapper;
import com.bluedigi.exam.customer.repository.CustomerRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CustomerService{
    private final CustomerRepository customerRepository;

    public List<CustomerResponseDTO> getAllCustomers() {
        return customerRepository.findAll().stream().map(entity -> CustomerMapper.toResponseDTO(entity)).toList();
    }

    public CustomerResponseDTO getCustomerById(Long id) {
        return CustomerMapper.toResponseDTO(customerRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found")));
    }

    public CustomerResponseDTO createCustomer(CustomerRequestDTO customer) {
        return CustomerMapper.toResponseDTO(customerRepository.save(CustomerMapper.toEntity(customer)));
    }

    public CustomerResponseDTO updateCustomer(Long id, CustomerRequestDTO customer) {
        CustomerEntity existingCustomer = customerRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found"));
        // Update the existing customer with the new data
        return CustomerMapper.toResponseDTO(customerRepository.save(existingCustomer));
    }

    public Integer deleteCustomer(Long id) {
        customerRepository.deleteById(id);
        return 1;
    }
    
    
}
