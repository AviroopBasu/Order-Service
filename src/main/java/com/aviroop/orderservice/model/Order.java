package com.aviroop.orderservice.model;

import lombok.*;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;


@Document(value = "order")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Setter
@Getter
public class Order {
    @Id
    private String id;
    private String customerId;
    private String productId;
    private int quantity;
    private String shippingAddress;
    private String paymentMethod;
}
