package com.example.demo.services

import com.example.demo.models.User

interface UserService {

    void save(User user)
    
    User findByUsername(String username)
    
}