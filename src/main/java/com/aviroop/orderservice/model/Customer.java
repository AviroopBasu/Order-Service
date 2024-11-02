package com.aviroop.orderservice.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(value = "customer")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Setter
@Getter
public class Customer {

    @Id
    private String id;
    private String name;
    private String address;
    private String email;
    private String phone;
    private String password;
}
