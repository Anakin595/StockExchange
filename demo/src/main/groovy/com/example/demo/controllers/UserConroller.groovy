package com.example.demo.controllers

import com.example.demo.models.User
import com.example.demo.services.SecurityService
import com.example.demo.services.UserService
import com.example.demo.validate.UserValidator
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class UserConroller {
    
    private UserService userService
    
    private SecurityService securityService 
    
    private UserValidator validator

    @Autowired
    UserConroller(UserService userService, SecurityService securityService, UserValidator validator) {
        this.userService = userService
        this.securityService = securityService
        this.validator = validator
    }
    
    @GetMapping('/registration')
    void registration(Model model) {
        model.addAttribute("useForm", new User())
        
    }
    
    @PostMapping('/registration')
    void registration(@ModelAttribute('useForm') User userForm, BindingResult result, Model model) {
        validator.validate(userForm, result)
        
        if(result.hasErrors()) {
            return
        }
        userService.save(userForm)
        securityService.autologin(userForm.username, userForm.passwordConfirm)
        
    }
    
    @GetMapping('/login')
    void login(Model model, String error, String logout) {
        if (error != null) {
            model.addAttribute("error", "Your username and password is invalid.")
        }
        if (logout != null) {
            model.addAttribute("message", "You have been logged out successfully.")
        }
    }

    @GetMapping(["/", "/welcome"])
    String welcome(Model model) {
        "welcome"
    }

    @GetMapping('/private')
    String privMethod(Model model) {
        "private method!"
    }
    
}
