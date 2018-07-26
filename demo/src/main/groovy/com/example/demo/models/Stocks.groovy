package com.example.demo.models

class Stocks {

    Date publicationDate

    Collection<Stock> items

    @Override
    String toString() {
        publicationDate.toString() + ": " + items.price.toString()
    }


}
