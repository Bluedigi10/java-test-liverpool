package com.bluedigi.exam.customer.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class CustomerRequestDTO {
    @NotBlank(message = "First name is required")
    private String firstName;
    private String middleName;
    @NotBlank(message = "Paternal last name is required")
    private String paternalLastName;
    @NotBlank(message = "Maternal last name is required")
    private String maternalLastName;
    @Email(message = "Email should be valid")
    @NotBlank(message = "Email is required")
    private String email;
    @NotBlank(message = "Shipping address is required")
    private String shippingAddress;

    // Getters and Setters
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
}
