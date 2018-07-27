package com.example.demo.repository

import com.example.demo.models.Stocks
import org.springframework.data.mongodb.repository.MongoRepository

interface StocksRepository extends MongoRepository<Stocks, String> {

    Stocks findByPublicationDate(Date date)
    
    
}