package com.bluedigi.exam.order.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.bluedigi.exam.order.dto.OrderRequestDTO;
import com.bluedigi.exam.order.dto.OrderResponseDTO;
import com.bluedigi.exam.order.service.OrderService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/orders")
@Tag(name = "Order", description = "API for management orders")   
public class OrderController {
    
    private final OrderService orderService;
    
    @GetMapping
    @Operation(
        summary = "Get all orders",
        description = "Returns the list of registered orders."
    )
    public List<OrderResponseDTO> getAllOrders() {
        return orderService.getAllOrders();
    }
    
    @GetMapping("/{id}")
    @Operation(
        summary = "Get order by ID",
        description = "Returns the order with the specified ID."
    )
    public OrderResponseDTO getOrderById(@PathVariable Long id) {
        return orderService.getOrderById(id);
    }

    @PostMapping
    @Operation(
        summary = "Create order",
        description = "Creates a new order."
    )
    @ResponseStatus(HttpStatus.CREATED)
    public OrderResponseDTO createOrder(@Valid @RequestBody OrderRequestDTO request) {
        return orderService.createOrder(request);
    }

    @PutMapping("/{id}")
    @Operation(
        summary = "Update order",
        description = "Updates the order with the specified ID."
    )
    public OrderResponseDTO updateOrder(@PathVariable Long id, @Valid @RequestBody OrderRequestDTO request) {
        return orderService.updateOrder(id, request);
    }

    @DeleteMapping("/{id}")
    @Operation(
        summary = "Delete order",
        description = "Deletes the order with the specified ID."
    )
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
    }

}
