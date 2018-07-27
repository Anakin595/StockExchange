package com.example.demo.controllers

import com.example.demo.exceptions.UserAlreadyRegisteredException
import com.example.demo.models.User
import com.example.demo.repository.UserRepository
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

import javax.servlet.http.HttpServletResponse
import javax.validation.Valid

@Slf4j
@RestController
class UserConroller {
    
    private UserRepository userRepository
    
    @Autowired
    UserConroller(UserRepository userRepository) {
        this.userRepository = userRepository
    }
    
    @PostMapping('/registration')
    void registration(@RequestBody @Valid User user, HttpServletResponse response) {
        def repoUser = userRepository.findByUsername(user.username)
        if(repoUser) {
            log.info("User already registered")
            throw new UserAlreadyRegisteredException()
        }
        userRepository.save(user)
        response.setStatus(HttpServletResponse.SC_CREATED)
        log.info("user registeres succesfully")
    }
    
}
