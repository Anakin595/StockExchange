package com.example.demo.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.CONFLICT)
class UserAlreadyRegisteredException extends Exception{
    
    UserAlreadyRegisteredException(String message) {
        super(message)
    }
    
}
