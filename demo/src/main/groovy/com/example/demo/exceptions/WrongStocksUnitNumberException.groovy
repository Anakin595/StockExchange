package com.example.demo.exceptions

import groovy.transform.CompileStatic
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@CompileStatic
@ResponseStatus(HttpStatus.BAD_REQUEST)
class WrongStocksUnitNumberException extends Exception{
    WrongStocksUnitNumberException(String message) {
        super(message)
    }
    
}
