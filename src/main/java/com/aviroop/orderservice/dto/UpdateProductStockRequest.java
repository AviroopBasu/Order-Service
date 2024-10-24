package com.aviroop.orderservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UpdateProductStockRequest {
    @JsonProperty(value = "updatedStock" , required = true)
    private int updatedStock;
}
