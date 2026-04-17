package com.bluedigi.exam.customer.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bluedigi.exam.customer.dto.CustomerRequestDTO;
import com.bluedigi.exam.customer.dto.CustomerResponseDTO;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/api/v1/customers")
public class CustomerController {
    @GetMapping
    public List<CustomerResponseDTO> getAllCustomers() {
        List<CustomerResponseDTO> customers = new ArrayList<>();
        return customers;
    }
    
    @GetMapping("/{id}")
    public String getCustomerById(@PathVariable Long id) {
        return "Cliente con ID: " + id;
    }

    @PostMapping
    public CustomerResponseDTO createCustomer(@PathVariable String id, @RequestBody CustomerRequestDTO request) {
        CustomerResponseDTO response = new CustomerResponseDTO();
        
        return response;
    }

    @PutMapping("/{id}")
    public CustomerResponseDTO updateCustomer(@PathVariable String id, @RequestBody CustomerRequestDTO request) {
        CustomerResponseDTO response = new CustomerResponseDTO();
        
        return response;
    }
}
