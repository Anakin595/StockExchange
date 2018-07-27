package com.example.demo.repository

import com.example.demo.models.UserStock
import org.springframework.data.mongodb.repository.MongoRepository

interface UserStocksRepository extends MongoRepository<UserStock, String> {
    
    Collection<UserStock> findAllByUser(String username)
    
}