package com.aviroop.orderservice.service;

import com.aviroop.orderservice.dto.OrderRequest;
import com.aviroop.orderservice.dto.ProductResponse;
import com.aviroop.orderservice.dto.UpdateProductStockRequest;
import com.aviroop.orderservice.model.Order;
import com.aviroop.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.protocol.types.Field;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.net.http.HttpResponse;
//import org.springframework.web.reactive.function.client.WebClient;


@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private WebClient webClient;
    @Autowired
    private RestTemplate restTemplate;
    @Value("${product.service.url}")
    private String productServiceUrl;

    public String placeOrder(OrderRequest orderRequest) throws Exception {
        // First we do basic Request validations
        if(orderRequest.getCustomerId() != null && orderRequest.getProductId() !=null && orderRequest.getQuantity() != 0) {
            // Check if customerID is valid.. We will do it once we have customer ms ready
            // Check if productId is valid.. We will now do a Sync call to Order MS and validate
            ProductResponse productResponse =
                    webClient.get()
                            .uri("/api/product/{productId}", orderRequest.getProductId())
                            .retrieve()
                            .bodyToMono(ProductResponse.class).block();
            if(productResponse!=null) {
                log.info("Order ID product fetched correctly from database");
                if(productResponse.getStock()==0 || orderRequest.getQuantity() > productResponse.getStock()) {
                    return "Sorry the stock for this product for this quantity has been exhausted ! Please try after some time";
                }
                Order order = Order.builder()
                        .customerId(orderRequest.getCustomerId())
                        .productId(orderRequest.getProductId())
                        .quantity(orderRequest.getQuantity())
                        .shippingAddress(orderRequest.getShippingAddress())
                        .paymentMethod(orderRequest.getPaymentMethod())
                        .build();
                orderRepository.save(order);

                // Update the stock by calling Product Microservice
               updateProductStock(productResponse,orderRequest);

                return "Order with ID : "+order.getId() +" has been created for product name as "+ productResponse.getName();
            }
            else {
                return "PRODUCT ID NOT FOUND";
            }

        } else {
            throw new Exception("Order not created");
        }
    }

    private void updateProductStock(ProductResponse productResponse , OrderRequest orderRequest) {
        UpdateProductStockRequest updateProductStockRequest = UpdateProductStockRequest.builder().updatedStock(productResponse.getStock() - orderRequest.getQuantity()).build();
        //RestTemplate to call Products MS
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        //Create HTTPENTITY
        HttpEntity<UpdateProductStockRequest> requestHttpEntity = new HttpEntity<>(updateProductStockRequest, httpHeaders);

        //Use exchange
        ResponseEntity<Void> responseEntity = restTemplate.exchange(
                productServiceUrl + "/api/product/{productId}",
                HttpMethod.PUT,
                requestHttpEntity,
                Void.class,
                orderRequest.getProductId()
        );
        //Handling Response
        if (HttpStatus.OK == responseEntity.getStatusCode()) {
            log.info("Product Stock has been updated successfully");
        } else {
            log.info("Failed to update product stock");
        }

    }
}
