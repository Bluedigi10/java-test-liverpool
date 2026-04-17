package com.bluedigi.exam.customer.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.bluedigi.exam.customer.dto.CustomerRequestDTO;
import com.bluedigi.exam.customer.dto.CustomerResponseDTO;
import com.bluedigi.exam.customer.entity.CustomerEntity;
import com.bluedigi.exam.customer.exception.CustomerException;
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
        return CustomerMapper.toResponseDTO(getCustomerEntityById(id));
    }

    public CustomerResponseDTO createCustomer(CustomerRequestDTO customer) {
        return CustomerMapper.toResponseDTO(customerRepository.save(CustomerMapper.toEntity(customer)));
    }

    public CustomerResponseDTO updateCustomer(Long id, CustomerRequestDTO customer) {
        CustomerEntity existingCustomer = getCustomerEntityById(id);
        // Update the existing customer with the new data
        return CustomerMapper.toResponseDTO(customerRepository.save(CustomerMapper.toEntity(existingCustomer, customer)));
    }

    public void deleteCustomer(Long id) {
        getCustomerEntityById(id);
        customerRepository.deleteById(id);
    }
    
    private CustomerEntity getCustomerEntityById(Long id) {
        return customerRepository.findById(id).orElseThrow(() -> new CustomerException(HttpStatus.NOT_FOUND, "Customer not found"));
    }
    
}
