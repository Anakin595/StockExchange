package com.example.demo.services

import com.example.demo.exceptions.UserAlreadyRegisteredException
import com.example.demo.models.User
import com.example.demo.repository.UserRepository
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

import javax.servlet.http.HttpServletResponse

@Slf4j
@Service
class UserService {

    @Autowired
    private UserRepository userRepository

    HttpServletResponse registration(User user, HttpServletResponse response) {
        def repoUser = userRepository.findByUsername(user.username)
        if(repoUser) {
            log.info("User registration failed.")
            throw new UserAlreadyRegisteredException("User already registered.")
        }
        userRepository.save(user)
        response.setStatus(HttpServletResponse.SC_CREATED)
        log.info("User registered successfully")
        response
    }

    User updateUser(User user, String username) {
        userRepository.findByUsername()
    }

    User changePassword(String oldPassword, String newPassword, String username) {

    }
}
