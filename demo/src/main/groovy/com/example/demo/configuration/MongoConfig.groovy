package com.example.demo.configuration

import com.mongodb.Mongo
import com.mongodb.MongoClient
import org.springframework.context.annotation.Bean
import org.springframework.data.mongodb.core.MongoTemplate

class MongoConfig {

    @Bean
    Mongo mongo() throws Exception {
        return new MongoClient("localhost")
    }

    @Bean
    MongoTemplate mongoTemplate() throws Exception {
        return new MongoTemplate(mongo() as MongoClient, "test")
    }
    
    
}
