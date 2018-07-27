package com.example.demo.models

import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.data.annotation.Id

import javax.validation.constraints.NotNull

class User {
    
    @Id
    String id
    
    @NotNull
    String username
    
    @NotNull
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    String password

    String name

    String lastname

    String email
    
}
