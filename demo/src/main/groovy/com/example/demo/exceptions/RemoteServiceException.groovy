package com.example.demo.exceptions

import groovy.transform.CompileStatic
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@CompileStatic
@ResponseStatus(HttpStatus.NOT_FOUND)
class RemoteServiceException extends Exception {
    
    RemoteServiceException(Throwable e) {
        super(e)
    }
}
