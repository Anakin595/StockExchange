package com.example.demo.controllers

import com.example.demo.clients.WebTaskClient
import com.example.demo.models.Stocks
import com.example.demo.repository.StocksRepository
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Controller
import org.springframework.web.client.ResourceAccessException

@Controller
@Slf4j
class StocksWebSocketController {

    private SimpMessagingTemplate messagingTemplate
    
    private StocksRepository stocksRepository
    
    private WebTaskClient webTaskClient

    @Autowired
    StocksWebSocketController(SimpMessagingTemplate messagingTemplate, StocksRepository stocksRepository, WebTaskClient webTaskClient) {
        this.messagingTemplate = messagingTemplate
        this.stocksRepository = stocksRepository
        this.webTaskClient = webTaskClient
    }

    @Scheduled(fixedRate = 5000l)
    private void scheduledStockUpdating() {
        try {
            def stocks = getCurrentStocks()
            def stocksFound = stocksRepository.findByPublicationDate(stocks.publicationDate)
            if (!stocksFound) {
                stocksRepository.insert(stocks)
                this.messagingTemplate.convertAndSend('/wsoc/stocks', stocks)
            }
            log.info(stocks.toString())
        } catch(ResourceAccessException e) {
            log.info("Stock update failed", e)
        }
    }
    
    private Stocks getCurrentStocks() {
        webTaskClient.getWebTaskStocks()
    }

}
