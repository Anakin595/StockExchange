package com.example.demo.controllers

import com.example.demo.models.User
import com.example.demo.services.UserService
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

import javax.servlet.http.HttpServletResponse
import javax.validation.Valid

@Slf4j
@RestController
class UserConroller {
    
    private UserService userService

    @Autowired
    UserConroller(UserService userService) {
        this.userService = userService
    }
    
    @PostMapping('/registration')
    void registration(@RequestBody @Valid User user, HttpServletResponse response) {
        userService.registration(user, response)
    }

    @PreAuthorize('isAuthenticated()')
    @PutMapping('/update')
    User updateUser(@RequestBody User user) {
        userService.updateUser(user, getCurrentUser())
    }

    @PreAuthorize('isAuthenticated()')
    @PutMapping('/changepassword')
    User changePassword(@RequestParam('old') String oldPassword, @RequestParam('new') String newPassword) {
        userService.changePassword(oldPassword, newPassword, getCurrentUser())
    }

    private String getCurrentUser() {
        SecurityContextHolder
    }
    
}
