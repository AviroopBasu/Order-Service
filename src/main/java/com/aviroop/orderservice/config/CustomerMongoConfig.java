package com.aviroop.orderservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackages = "com.aviroop.orderservice.repository.customer",
        mongoTemplateRef = "customerMongoTemplate")
public class CustomerMongoConfig {
    @Bean(name = "customerMongoTemplate")
    public MongoTemplate customerMongoTemplate() {
        MongoDatabaseFactory factory = new SimpleMongoClientDatabaseFactory("mongodb://localhost:27017/customer-service");
        return new MongoTemplate(factory);
    }
}
