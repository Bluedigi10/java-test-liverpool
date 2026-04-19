package com.bluedigi.exam.order.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import com.bluedigi.exam.order.client.CustomerClient;
import com.bluedigi.exam.order.dto.OrderRequestDTO;
import com.bluedigi.exam.order.dto.OrderResponseDTO;
import com.bluedigi.exam.order.entity.OrderEntity;
import com.bluedigi.exam.order.exception.OrderException;
import com.bluedigi.exam.order.repository.OrderRepository;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private CustomerClient customerClient;

    @InjectMocks
    private OrderService orderService;

    @Test
    void getAllOrdersShouldReturnOrderList() {
        when(orderRepository.findAll()).thenReturn(List.of(orderEntity(1L, 1L)));

        List<OrderResponseDTO> orders = orderService.getAllOrders();

        assertThat(orders).hasSize(1);
        assertThat(orders.get(0).getOrderId()).isEqualTo(1L);
        assertThat(orders.get(0).getCustomerId()).isEqualTo(1L);
    }

    @Test
    void getOrderByIdWhenOrderExistsShouldReturnOrder() {
        when(orderRepository.findById(1L)).thenReturn(Optional.of(orderEntity(1L, 1L)));

        OrderResponseDTO order = orderService.getOrderById(1L);

        assertThat(order.getOrderId()).isEqualTo(1L);
        assertThat(order.getCustomerId()).isEqualTo(1L);
    }

    @Test
    void getOrderByIdWhenOrderDoesNotExistShouldThrowNotFound() {
        when(orderRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> orderService.getOrderById(99L))
                .isInstanceOf(OrderException.class)
                .hasMessage("Order with ID: 99 not found")
                .extracting("code")
                .isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void createOrderWhenCustomerExistsShouldCreateOrder() {
        OrderRequestDTO request = orderRequest(1L);
        when(orderRepository.save(any(OrderEntity.class))).thenReturn(orderEntity(1L, 1L));

        OrderResponseDTO order = orderService.createOrder(request);

        assertThat(order.getOrderId()).isEqualTo(1L);
        assertThat(order.getCustomerId()).isEqualTo(1L);
        verify(customerClient).validateCustomerExists(1L);
        verify(orderRepository).save(any(OrderEntity.class));
    }

    @Test
    void createOrderWhenCustomerDoesNotExistShouldThrowNotFound() {
        OrderRequestDTO request = orderRequest(99L);
        doThrow(new OrderException(HttpStatus.NOT_FOUND, "Customer with ID 99 not found."))
                .when(customerClient).validateCustomerExists(99L);

        assertThatThrownBy(() -> orderService.createOrder(request))
                .isInstanceOf(OrderException.class)
                .hasMessage("Customer with ID 99 not found.")
                .extracting("code")
                .isEqualTo(HttpStatus.NOT_FOUND);

        verify(orderRepository, never()).save(any(OrderEntity.class));
    }

    @Test
    void updateOrderWhenCustomerExistsShouldUpdateOrder() {
        OrderRequestDTO request = orderRequest(2L);
        OrderEntity existingOrder = orderEntity(1L, 1L);
        when(orderRepository.findById(1L)).thenReturn(Optional.of(existingOrder));
        when(orderRepository.save(any(OrderEntity.class))).thenReturn(existingOrder);

        OrderResponseDTO order = orderService.updateOrder(1L, request);

        assertThat(order.getOrderId()).isEqualTo(1L);
        assertThat(order.getCustomerId()).isEqualTo(2L);
        verify(customerClient).validateCustomerExists(2L);
        verify(orderRepository).save(existingOrder);
    }

    @Test
    void updateOrderWhenOrderDoesNotExistShouldThrowNotFound() {
        OrderRequestDTO request = orderRequest(1L);
        when(orderRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> orderService.updateOrder(99L, request))
                .isInstanceOf(OrderException.class)
                .hasMessage("Order with ID: 99 not found")
                .extracting("code")
                .isEqualTo(HttpStatus.NOT_FOUND);

        verify(customerClient, never()).validateCustomerExists(any());
        verify(orderRepository, never()).save(any(OrderEntity.class));
    }

    @Test
    void updateOrderWhenCustomerDoesNotExistShouldThrowNotFound() {
        OrderRequestDTO request = orderRequest(99L);
        when(orderRepository.findById(1L)).thenReturn(Optional.of(orderEntity(1L, 1L)));
        doThrow(new OrderException(HttpStatus.NOT_FOUND, "Customer with ID 99 not found."))
                .when(customerClient).validateCustomerExists(99L);

        assertThatThrownBy(() -> orderService.updateOrder(1L, request))
                .isInstanceOf(OrderException.class)
                .hasMessage("Customer with ID 99 not found.")
                .extracting("code")
                .isEqualTo(HttpStatus.NOT_FOUND);

        verify(orderRepository, never()).save(any(OrderEntity.class));
    }

    @Test
    void deleteOrderWhenOrderExistsShouldDeleteOrder() {
        when(orderRepository.findById(1L)).thenReturn(Optional.of(orderEntity(1L, 1L)));

        orderService.deleteOrder(1L);

        verify(orderRepository).deleteById(1L);
    }

    @Test
    void deleteOrderWhenOrderDoesNotExistShouldThrowNotFound() {
        when(orderRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> orderService.deleteOrder(99L))
                .isInstanceOf(OrderException.class)
                .hasMessage("Order with ID: 99 not found")
                .extracting("code")
                .isEqualTo(HttpStatus.NOT_FOUND);

        verify(orderRepository, never()).deleteById(99L);
    }

    private OrderRequestDTO orderRequest(Long customerId) {
        OrderRequestDTO request = new OrderRequestDTO();
        request.setProductCode("123");
        request.setCustomerId(customerId);
        request.setQuantity(1);
        request.setPrice(new BigDecimal("0.01"));
        return request;
    }

    private OrderEntity orderEntity(Long orderId, Long customerId) {
        OrderEntity entity = new OrderEntity();
        entity.setOrderId(orderId);
        entity.setProductCode("123");
        entity.setCustomerId(customerId);
        entity.setQuantity(1);
        entity.setPrice(new BigDecimal("0.01"));
        entity.setCreatedAt(LocalDateTime.of(2026, 4, 18, 22, 25));
        entity.setUpdatedAt(LocalDateTime.of(2026, 4, 18, 22, 25));
        return entity;
    }
}
