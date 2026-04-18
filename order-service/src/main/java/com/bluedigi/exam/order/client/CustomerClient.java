package com.bluedigi.exam.order.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;

import com.bluedigi.exam.order.exception.OrderException;

@Component
public class CustomerClient {
    private final RestClient restClient;
    private final String customerServiceUrl;

    public CustomerClient(
        RestClient restClient,
        @Value("${customer.service.url}") String customerServiceUrl
    ) {
        this.restClient = restClient;
        this.customerServiceUrl = customerServiceUrl;
    }

    public void validateCustomerExists(Long customerId){
        try {
            restClient.get()
                .uri(customerServiceUrl + "/api/v1/customers/{id}", customerId)
                .retrieve()
                .toBodilessEntity();
        } catch (HttpClientErrorException ex) {
            throw new OrderException(HttpStatus.NOT_FOUND, "Customer with ID " + customerId + " not found.");
        }
    }
}
