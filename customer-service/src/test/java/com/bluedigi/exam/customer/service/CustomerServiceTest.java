package com.bluedigi.exam.customer.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import com.bluedigi.exam.customer.dto.CustomerRequestDTO;
import com.bluedigi.exam.customer.dto.CustomerResponseDTO;
import com.bluedigi.exam.customer.entity.CustomerEntity;
import com.bluedigi.exam.customer.exception.CustomerException;
import com.bluedigi.exam.customer.repository.CustomerRepository;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerService customerService;

    @Test
    void getAllCustomersShouldReturnCustomerList() {
        when(customerRepository.findAll()).thenReturn(List.of(customerEntity(1L, "emmanuel@example.com")));

        List<CustomerResponseDTO> customers = customerService.getAllCustomers();

        assertThat(customers).hasSize(1);
        assertThat(customers.get(0).getCustomerId()).isEqualTo(1L);
        assertThat(customers.get(0).getEmail()).isEqualTo("emmanuel@example.com");
    }

    @Test
    void getCustomerByIdWhenCustomerExistsShouldReturnCustomer() {
        when(customerRepository.findById(1L)).thenReturn(Optional.of(customerEntity(1L, "emmanuel@example.com")));

        CustomerResponseDTO customer = customerService.getCustomerById(1L);

        assertThat(customer.getCustomerId()).isEqualTo(1L);
        assertThat(customer.getEmail()).isEqualTo("emmanuel@example.com");
    }

    @Test
    void getCustomerByIdWhenCustomerDoesNotExistShouldThrowNotFound() {
        when(customerRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> customerService.getCustomerById(99L))
                .isInstanceOf(CustomerException.class)
                .hasMessage("Customer not found")
                .extracting("code")
                .isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void createCustomerWhenEmailDoesNotExistShouldCreateCustomer() {
        CustomerRequestDTO request = customerRequest("emmanuel@example.com");
        when(customerRepository.existsByEmail("emmanuel@example.com")).thenReturn(false);
        when(customerRepository.save(any(CustomerEntity.class))).thenReturn(customerEntity(1L, "emmanuel@example.com"));

        CustomerResponseDTO customer = customerService.createCustomer(request);

        assertThat(customer.getCustomerId()).isEqualTo(1L);
        assertThat(customer.getEmail()).isEqualTo("emmanuel@example.com");
        verify(customerRepository).save(any(CustomerEntity.class));
    }

    @Test
    void createCustomerWhenEmailAlreadyExistsShouldThrowConflict() {
        CustomerRequestDTO request = customerRequest("emmanuel@example.com");
        when(customerRepository.existsByEmail("emmanuel@example.com")).thenReturn(true);

        assertThatThrownBy(() -> customerService.createCustomer(request))
                .isInstanceOf(CustomerException.class)
                .hasMessage("Email already exists")
                .extracting("code")
                .isEqualTo(HttpStatus.CONFLICT);

        verify(customerRepository, never()).save(any(CustomerEntity.class));
    }

    @Test
    void updateCustomerWhenEmailBelongsToSameCustomerShouldUpdateCustomer() {
        CustomerRequestDTO request = customerRequest("emmanuel@example.com");
        CustomerEntity existingCustomer = customerEntity(1L, "emmanuel@example.com");
        when(customerRepository.findById(1L)).thenReturn(Optional.of(existingCustomer));
        when(customerRepository.findByEmail("emmanuel@example.com")).thenReturn(Optional.of(existingCustomer));
        when(customerRepository.save(any(CustomerEntity.class))).thenReturn(existingCustomer);

        CustomerResponseDTO customer = customerService.updateCustomer(1L, request);

        assertThat(customer.getCustomerId()).isEqualTo(1L);
        assertThat(customer.getEmail()).isEqualTo("emmanuel@example.com");
        verify(customerRepository).save(existingCustomer);
    }

    @Test
    void updateCustomerWhenEmailBelongsToAnotherCustomerShouldThrowConflict() {
        CustomerRequestDTO request = customerRequest("duplicated@example.com");
        when(customerRepository.findById(1L)).thenReturn(Optional.of(customerEntity(1L, "emmanuel@example.com")));
        when(customerRepository.findByEmail("duplicated@example.com"))
                .thenReturn(Optional.of(customerEntity(2L, "duplicated@example.com")));

        assertThatThrownBy(() -> customerService.updateCustomer(1L, request))
                .isInstanceOf(CustomerException.class)
                .hasMessage("Email already exists")
                .extracting("code")
                .isEqualTo(HttpStatus.CONFLICT);

        verify(customerRepository, never()).save(any(CustomerEntity.class));
    }

    @Test
    void deleteCustomerWhenCustomerExistsShouldDeleteCustomer() {
        when(customerRepository.findById(1L)).thenReturn(Optional.of(customerEntity(1L, "emmanuel@example.com")));

        customerService.deleteCustomer(1L);

        verify(customerRepository).deleteById(1L);
    }

    @Test
    void deleteCustomerWhenCustomerDoesNotExistShouldThrowNotFound() {
        when(customerRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> customerService.deleteCustomer(99L))
                .isInstanceOf(CustomerException.class)
                .hasMessage("Customer not found")
                .extracting("code")
                .isEqualTo(HttpStatus.NOT_FOUND);

        verify(customerRepository, never()).deleteById(99L);
    }

    private CustomerRequestDTO customerRequest(String email) {
        CustomerRequestDTO request = new CustomerRequestDTO();
        request.setFirstName("Emmanuel");
        request.setMiddleName("David");
        request.setPaternalLastName("Martinez");
        request.setMaternalLastName("Vidal");
        request.setEmail(email);
        request.setShippingAddress("Nicaragua 39");
        return request;
    }

    private CustomerEntity customerEntity(Long id, String email) {
        CustomerEntity entity = new CustomerEntity();
        entity.setCustomerId(id);
        entity.setFirstName("Emmanuel");
        entity.setMiddleName("David");
        entity.setPaternalLastName("Martinez");
        entity.setMaternalLastName("Vidal");
        entity.setEmail(email);
        entity.setShippingAddress("Nicaragua 39");
        entity.setCreatedAt(LocalDateTime.of(2026, 4, 18, 22, 25));
        entity.setUpdatedAt(LocalDateTime.of(2026, 4, 18, 22, 25));
        return entity;
    }
}
