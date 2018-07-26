package com.example.demo.repository

import com.example.demo.models.Account
import com.example.demo.repository.mappers.AccountRowMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Repository

import java.sql.SQLException

@Repository
class AccountsRepository {

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate

    private AccountRowMapper mapper = new AccountRowMapper()

    Account findById(Long id) {
        def params = [id: id]
        def query = SqlQueries.GET_ACCOUNT_QUERY
        query += "WHERE ID = :id"

        try{
            jdbcTemplate.queryForObject(query, params, new AccountRowMapper()) as Account
        } catch (SQLException e) {
            throw new SQLException(e)
        }
    }


}
