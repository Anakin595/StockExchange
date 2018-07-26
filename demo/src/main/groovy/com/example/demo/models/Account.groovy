package com.example.demo.models

import com.fasterxml.jackson.annotation.JsonIgnore

class Account {

    Long id

    String username

    @JsonIgnore
    String password

    String name

    String lastname

    Long money

}
