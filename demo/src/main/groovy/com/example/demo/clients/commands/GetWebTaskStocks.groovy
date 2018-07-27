package com.example.demo.clients.commands

import com.example.demo.models.Stocks
import com.netflix.hystrix.HystrixCommand
import com.netflix.hystrix.HystrixCommand.Setter as HystrixSetter
import com.netflix.hystrix.HystrixCommandGroupKey
import com.netflix.hystrix.HystrixCommandKey
import org.springframework.web.client.RestTemplate

class GetWebTaskStocks extends HystrixCommand<Stocks> {
    
    
    GetWebTaskStocks() {
        super(HystrixSetter.withGroupKey(
                HystrixCommandGroupKey.Factory.asKey("WebTaskClient"))
                .andCommandKey(HystrixCommandKey.Factory.asKey("GetWebTaskStocks")))
    }
    
    @Override
    protected Stocks run() throws Exception {
        final String uri =  'http://webtask.future-processing.com:8068/stocks'
        RestTemplate restTemplate = new RestTemplate()
        def result = restTemplate.getForObject(uri, Stocks.class)
        result
    }
}
