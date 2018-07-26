package com.example.demo.models

import javax.validation.constraints.NotNull


class Account {

    Long id

    @NotNull
    Credentials credentials

    String name

    String lastname

    Long money

}
