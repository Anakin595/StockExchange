package com.example.demo.controllers

import com.example.demo.exceptions.InsufficientResourcesException
import com.example.demo.exceptions.WrongStocksUnitNumberException
import com.example.demo.models.Stocks
import com.example.demo.models.UserStock
import com.example.demo.repository.StocksRepository
import com.example.demo.repository.UserStocksRepository
import com.example.demo.validate.StocksValidator
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

import javax.validation.Valid

@Slf4j
@RequestMapping('/stocks')
@RestController
class StocksController {

    private final static String BASIC_CURRENCY = "PLN"
    
    
    private StocksRepository stocksRepository
    
    private UserStocksRepository userStocksRepository

    @Autowired
    StocksController(StocksRepository stocksRepository, UserStocksRepository userStocksRepository) {
        this.stocksRepository = stocksRepository
        this.userStocksRepository = userStocksRepository
    }
    
    @GetMapping
    Collection<Stocks> getStocks(@RequestParam('pageSize') int pageSize) {
        def sort = new Sort(Sort.Direction.DESC, "publicationDate")
        Pageable pageable = new PageRequest(0,  pageSize?: 10, sort)
        stocksRepository.findAll(pageable) as Collection<Stocks>
    }

    @PostMapping
    Stocks putTestStocks(@RequestBody @Valid Stocks stocks) {
        def stocksFound = stocksRepository.findByPublicationDate(stocks.publicationDate)
        if (!stocksFound) {
            stocksRepository.insert(stocks)
        }
        stocksRepository.findByPublicationDate(stocks.publicationDate)
    }
    
    @PreAuthorize('isAuthenticated()')
    @GetMapping('/user')
    Collection<UserStock> getUserStocks() {
        def username = getCurrentUser()
        def userStocks = userStocksRepository.findAllByUser(username)
        if(!userStocks) {
            return []
        }
        userStocks.findAll { it.code != BASIC_CURRENCY } as Collection<UserStock>
    }

    @PreAuthorize('isAuthenticated()')
    @GetMapping('/user/wallet')
    UserStock getUserWallet(){
        def username = getCurrentUser()
        userStocksRepository.findAllByUser(username).find {it.code == BASIC_CURRENCY}
    }

    @PreAuthorize('isAuthenticated()')
    @PostMapping('/user/wallet')
    UserStock setOrUpdateUserWallet(@RequestParam('money') BigDecimal money) {
        def wallet = getUserWallet()
        if(!wallet) {
            wallet = new UserStock(
                    user: getCurrentUser(),
                    code: BASIC_CURRENCY,
            )
        }
        wallet.amount = money
        userStocksRepository.save(wallet)
        wallet
    }

    @PreAuthorize('isAuthenticated()')
    @PostMapping('/user/buy/{stockCode}')
    UserStock buyStocks(@PathVariable('stockCode') String stockCode, @RequestParam('units') int units) {
        def stockInfo = getStocks(1)[0].items.find { it.code == stockCode }
        def userWallet  = getUserWallet()
        def userCurrentMoney = userWallet?.amount ?: 0 as BigDecimal
        def stockValue = (stockInfo.price * units) as BigDecimal
        def newUserMoney = userCurrentMoney - stockValue
        def userStock = getUserStocks().find { it.code == stockCode }
        
        StocksValidator.validateBuying(newUserMoney, stockInfo.unit, units)
        
        setOrUpdateUserWallet(newUserMoney)
        log.info("bought [$units] units of ${stockInfo.name}($stockCode)")
        createOrUpdateStock(stockCode, userStock?.units ?: 0 + units)
    }

    @PreAuthorize('isAuthenticated()')
    @PostMapping('/user/sell/{stockCode}')
    UserStock sellStock(@PathVariable('stockCode') String stockCode, @RequestParam int units) {
        def userStock = getUserStocks().find { it.code == stockCode }
        def stockInfo = getStocks(1)[0].items.find { it.code == stockCode }
        def stockValue = (stockInfo.price * units) as BigDecimal
        def newUserMoney = getUserWallet()?.amount?:0 + stockValue
        
        StocksValidator.validateSelling(userStock, stockInfo.unit, units)
  
        setOrUpdateUserWallet(newUserMoney)
        log.info("Sold [$units] units of ${stockInfo.name}($stockCode) for ")
        createOrUpdateStock(stockCode, userStock?.units ?: 0 - units)
    }

    private UserStock createOrUpdateStock(String code, int units) {
        def userStock = getUserStocks()?.find { it.code == code}
        if(!userStock) {
            def username = getCurrentUser()
            userStock = new UserStock(
                    user: username,
                    code: code,
            )
        }
        userStock.units = units
        userStocksRepository.save(userStock)
        userStock
    }

    private static String getCurrentUser() {
        SecurityContextHolder.context.authentication.name as String
    }
    
}
