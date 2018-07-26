package com.example.demo.models

import com.fasterxml.jackson.annotation.JsonIgnore

import javax.validation.constraints.NotNull

class Credentials {
    
    @NotNull
    String username
    
    @NotNull
    @JsonIgnore
    String password
    
}
