package com.example.demo.validate

import com.example.demo.exceptions.InsufficientResourcesException
import com.example.demo.exceptions.StockDoNotExistException
import com.example.demo.exceptions.WrongStocksUnitNumberException
import com.example.demo.models.Stock
import com.example.demo.models.UserStock
import com.example.demo.services.StocksService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Lazy
import org.springframework.stereotype.Component

@Lazy
@Component
class StocksValidator {

    @Autowired
    private StocksService stocksService

    void validateBuying(BigDecimal userMoney, int stockUnit, int unitsToBuy) {
        validateMoneyResource(userMoney)
        validateUnits(stockUnit, unitsToBuy)
    }

    void validateSelling(UserStock userStock, int stockUnit, int unitsToBuy) {
        validateStockResource(userStock)
        validateUnits(stockUnit, unitsToBuy)
    }

    void validateRequest(String stockCode, int unitsToBuy) {
        def stockInfo = stocksService.getStocks(1)[0].items.find { it.code == stockCode }
        validateStockExistance(stockInfo, stockCode)
        validateUnits(stockInfo?.unit, unitsToBuy)
    }

    void validateStockExistance(Stock stock, String stockCode) {
        if(!stock) {
            throw new StockDoNotExistException("Stock with code $stockCode do not exist.")
        }
    }

    private void validateUnits(int stockUnits, int transactionUnits) {
        if( transactionUnits % stockUnits != 0 ) {
            throw new WrongStocksUnitNumberException("Incorrect units amount.")
        }
    }
    
    private validateMoneyResource(BigDecimal money) {
        if(money < 0) {
            throw new InsufficientResourcesException("Insufficient money.")
        }
    }
    private validateStockResource(UserStock stock) {
        if(!stock || stock.units <= 0) {
            throw new InsufficientResourcesException("Insufficient stock units.")
        } 
    }


}
