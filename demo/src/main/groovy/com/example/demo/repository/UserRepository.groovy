package com.example.demo.repository

import com.example.demo.models.User
import org.springframework.data.mongodb.repository.MongoRepository

interface UserRepository extends MongoRepository<User, String> {

    User findByUsername(String username)
    
}