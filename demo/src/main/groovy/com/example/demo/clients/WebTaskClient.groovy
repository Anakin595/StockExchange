package com.example.demo.clients

import com.example.demo.clients.commands.GetWebTaskStocks
import com.example.demo.exceptions.RemoteServiceException
import com.example.demo.models.Stocks
import com.netflix.hystrix.exception.HystrixRuntimeException
import com.netflix.hystrix.exception.HystrixTimeoutException
import org.springframework.stereotype.Component

import javax.naming.TimeLimitExceededException

@Component
class WebTaskClient {
    
    
    Stocks getWebTaskStocks() {
        try {
            new GetWebTaskStocks().execute()
        } catch(HystrixTimeoutException e){
            throw new TimeLimitExceededException("WebTask resources server error.")
        } catch(HystrixRuntimeException e) {
            throw new RemoteServiceException(e)
        }
    }
    

}