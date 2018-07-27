package com.example.demo.services

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
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service

@Slf4j
@Service
class StocksService {

    private final static String BASIC_CURRENCY = "PLN"

    private StocksRepository stocksRepository

    private UserStocksRepository userStocksRepository

    private StocksValidator validator

    @Autowired
    StocksController(StocksRepository stocksRepository,
                     UserStocksRepository userStocksRepository,
                     StocksValidator validator) {
        this.stocksRepository = stocksRepository
        this.userStocksRepository = userStocksRepository
        this.validator = validator
    }

    Collection<Stocks> getStocks(int pageSize) {
        def sort = new Sort(Sort.Direction.DESC, "publicationDate")
        Pageable pageable = new PageRequest(0,  pageSize?: 10, sort)
        stocksRepository.findAll(pageable) as Collection<Stocks>
    }

    Stocks putTestStocks(Stocks stocks) {
        def stocksFound = stocksRepository.findByPublicationDate(stocks.publicationDate)
        if (!stocksFound) {
            stocksRepository.insert(stocks)
        }
        stocksRepository.findByPublicationDate(stocks.publicationDate)
    }

    Collection<UserStock> getUserStocks() {
        def username = getCurrentUser()
        def userStocks = userStocksRepository.findAllByUser(username)
        if(!userStocks) {
            return []
        }
        userStocks.findAll { it.code != BASIC_CURRENCY } as Collection<UserStock>
    }

    UserStock getUserWallet(){
        def username = getCurrentUser()
        userStocksRepository.findAllByUser(username).find {it.code == BASIC_CURRENCY}
    }

    UserStock createOrUpdateUserWallet(BigDecimal money) {
        def wallet = getUserWallet()
        if(!wallet) {
            wallet = new UserStock(
                    user: getCurrentUser(),
                    code: BASIC_CURRENCY,
            )
        }
        wallet.money = money
        userStocksRepository.save(wallet)
        wallet
    }

    UserStock buyStocks(String stockCode, int units) {
        validator.validateRequest(stockCode, units)

        def stockInfo = getStocks(1)[0].items.find { it.code == stockCode }
        def stockValue = (stockInfo.price * units) as BigDecimal
        def newUserMoney = getUserWallet()?.money?:0 - stockValue

        def userStock = getUserStocks().find { it.code == stockCode }

        validator.validateBuying(newUserMoney, stockInfo.unit, units)

        createOrUpdateUserWallet(newUserMoney)
        log.info("bought [$units] units of ${stockInfo.name}($stockCode)")
        createOrUpdateStock(stockCode, userStock?.units ?: 0 + units)
    }

    UserStock sellStock(String stockCode, int units) {
        validator.validateRequest(stockCode, units)

        def stockInfo = getStocks(1)[0].items.find { it.code == stockCode }
        def stockValue = (stockInfo.price * units) as BigDecimal
        def newUserMoney = getUserWallet()?.money?:0 + stockValue

        def userStock = getUserStocks().find { it.code == stockCode }

        validator.validateSelling(userStock, stockInfo.unit, units)

        createOrUpdateUserWallet(newUserMoney)
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
