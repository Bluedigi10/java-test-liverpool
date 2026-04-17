package com.bluedigi.exam.customer.dto;

import java.time.LocalDateTime;

public class CustomerResponseDTO {
    private Long customerId;
    private String firstName;
    private String middleName;
    private String paternalLastName;
    private String maternalLastName;
    private String email;
    private String shippingAddress;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Getters and Setters
    
    public Long getCustomerId() {
        return customerId;
    }
    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getMiddleName() {
        return middleName;
    }
    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }
    public String getPaternalLastName() {
        return paternalLastName;
    }
    public void setPaternalLastName(String paternalLastName) {
        this.paternalLastName = paternalLastName;
    }
    public String getMaternalLastName() {
        return maternalLastName;
    }
    public void setMaternalLastName(String maternalLastName) {
        this.maternalLastName = maternalLastName;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getShippingAddress() {
        return shippingAddress;
    }
    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
