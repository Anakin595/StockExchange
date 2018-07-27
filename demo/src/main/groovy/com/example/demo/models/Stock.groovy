package com.example.demo.models

import javax.validation.constraints.NotNull

class Stock {

    @NotNull
    String name

    @NotNull
    String code

    @NotNull
    Integer unit

    @NotNull
    BigDecimal price

}
