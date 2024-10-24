package com.aviroop.orderservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequest {
    private String customerId;
    private String productId;
    private int quantity;
    private String shippingAddress;
    private String paymentMethod;
}
