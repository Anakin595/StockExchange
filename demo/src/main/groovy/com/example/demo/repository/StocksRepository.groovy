package com.example.demo.repository

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Repository

@Repository
class StocksRepository {

    @Autowired
    JdbcTemplate jdbcTemplate




}
