package com.aviroop.orderservice.config;

import io.netty.handler.proxy.HttpProxyHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {
    @Value("${product.service.url}")
    private String productServiceUrl;
    @Bean
    public WebClient webClient(WebClient.Builder builder) {
        return builder.baseUrl(productServiceUrl)
                .build();
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}


