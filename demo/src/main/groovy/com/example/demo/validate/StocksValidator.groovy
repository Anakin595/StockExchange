package com.example.demo.validate

import com.example.demo.exceptions.InsufficientResourcesException
import com.example.demo.exceptions.WrongStocksUnitNumberException
import com.example.demo.models.UserStock

class StocksValidator {

    static void validateBuying(BigDecimal userMoney, int stockUnit, int unitsToBuy) {
        validateMoneyResource(userMoney)
        validateUnits(stockUnit, unitsToBuy)
    }

    static void validateSelling(UserStock userStock, int stockUnit, int unitsToBuy) {
        validateStockResource(userStock)
        validateUnits(stockUnit, unitsToBuy)
    }
    
    private static void validateUnits(int stockUnits, int transactionUnits) {
        if( transactionUnits % stockUnits != 0 ) {
            throw new WrongStocksUnitNumberException("Incorrect units amount")
        }
    }
    
    private static validateMoneyResource(BigDecimal money) {
        if(money < 0) {
            throw new InsufficientResourcesException("Insufficient money")
        }
    }
    private static validateStockResource(UserStock stock) {
        if(!stock || stock.units <= 0) {
            throw new InsufficientResourcesException("Insufficient stock units.")
        } 
    }
}
