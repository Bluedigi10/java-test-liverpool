package com.bluedigi.exam.order.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class OrderRequestDTO {
    @NotBlank(message = "Product code is required")
    private String productCode;
    @NotNull(message = "Customer ID is required")
    private Long customerId;
    @NotNull(message = "Quantity is required")
    @Min(value = 1, message = "Quantity must be a positive integer")
    private Integer quantity;
    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.01", message = "Price must be a positive decimal")
    private BigDecimal price;

    // Getters and Setters

    public String getProductCode() {
        return productCode;
    }
    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }
    public Long getCustomerId() {
        return customerId;
    }
    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }
    public Integer getQuantity() {
        return quantity;
    }
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
    public BigDecimal getPrice() {
        return price;
    }
    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
