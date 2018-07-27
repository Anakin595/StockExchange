package com.example.demo.models

import com.fasterxml.jackson.annotation.JsonInclude
import org.springframework.data.annotation.Id

import javax.validation.constraints.NotNull

class UserStock {
    
    @Id
    String id
    
    @NotNull
    String user
    
    @NotNull
    String code
    
    @JsonInclude(JsonInclude.Include.NON_NULL)
    BigDecimal money

    @JsonInclude(JsonInclude.Include.NON_NULL)
    int units
    
    
}
