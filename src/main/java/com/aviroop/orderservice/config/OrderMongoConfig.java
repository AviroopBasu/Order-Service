package com.aviroop.orderservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackages = "com.aviroop.orderservice.repository.order",
        mongoTemplateRef = "orderMongoTemplate")
public class OrderMongoConfig {
    @Bean(name = "orderMongoTemplate")
    @Primary
    public MongoTemplate customerMongoTemplate() {
        MongoDatabaseFactory factory = new SimpleMongoClientDatabaseFactory("mongodb://localhost:27017/order-service");
        return new MongoTemplate(factory);
    }

}
