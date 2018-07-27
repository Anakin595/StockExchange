package com.example.demo.models

import org.springframework.data.annotation.Id

class Stocks {

    @Id
    String id
    
    Date publicationDate

    Collection<Stock> items


}
