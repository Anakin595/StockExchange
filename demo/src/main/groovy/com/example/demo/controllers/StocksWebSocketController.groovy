package com.example.demo.controllers

import com.example.demo.models.Stocks
import com.example.demo.models.TestResponse
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Controller
import org.springframework.web.client.RestTemplate

@Controller
@Slf4j
class StocksWebSocketController {

    private SimpMessagingTemplate messagingTemplate

    @Autowired
    StocksWebSocketController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate
    }

    @MessageMapping("/send")
    void getShares(String message) {
        this.messagingTemplate.convertAndSend('/chat', message)
    }

    //@Scheduled(fixedRate = 5000l)
    void scheduledTesting() {
        final String uri =  'http://webtask.future-processing.com:8068/stocks'

        RestTemplate restTemplate = new RestTemplate()
        def result = restTemplate.getForObject(uri, Stocks.class)
        this.messagingTemplate.convertAndSend('/stocks', new TestResponse(id: 1, name: 'DONE!'))
        log.info(result.toString())
    }

}
