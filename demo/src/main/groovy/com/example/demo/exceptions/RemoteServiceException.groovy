package com.example.demo.exceptions

import groovy.transform.CompileStatic

@CompileStatic
class RemoteServiceException extends Exception {
    
    RemoteServiceException(Throwable e) {
        super(e)
    }
}
