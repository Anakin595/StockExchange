package com.example.demo.controllers

import com.example.demo.models.Stocks
import com.example.demo.models.UserStock
import com.example.demo.services.StocksService
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

import javax.validation.Valid
import javax.validation.constraints.NotNull

@Slf4j
@RequestMapping('/stocks')
@RestController
class StocksController {

    private StocksService stocksService

    @Autowired
    StocksController(StocksService stocksService) {
        this.stocksService = stocksService
    }
    
    @GetMapping
    Collection<Stocks> getStocks(@RequestParam('pageSize') int pageSize = 1) {
        stocksService.getStocks(pageSize)
    }

    @PostMapping
    Stocks putTestStocks(@RequestBody @Valid Stocks stocks) {
        stocksService.putTestStocks(stocks)
    }
    
    @PreAuthorize('isAuthenticated()')
    @GetMapping('/user')
    Collection<UserStock> getUserStocks() {
        stocksService.getUserStocks()
    }

    @PreAuthorize('isAuthenticated()')
    @GetMapping('/user/wallet')
    UserStock getUserWallet(){
        stocksService.getUserWallet()
    }

    @PreAuthorize('isAuthenticated()')
    @PostMapping('/user/wallet')
    UserStock setOrUpdateUserWallet(@RequestParam('money') @NotNull BigDecimal money) {
        stocksService.createOrUpdateUserWallet(money)
    }

    @PreAuthorize('isAuthenticated()')
    @PostMapping('/user/buy/{stockCode}')
    UserStock buyStocks(@PathVariable('stockCode') String stockCode, @RequestParam('units') int units) {
        stocksService.buyStocks(stockCode, units)
    }

    @PreAuthorize('isAuthenticated()')
    @PostMapping('/user/sell/{stockCode}')
    UserStock sellStock(@PathVariable('stockCode') String stockCode, @RequestParam int units) {
        stocksService.sellStock(stockCode, units)
    }

}
