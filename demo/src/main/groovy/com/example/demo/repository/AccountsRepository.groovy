package com.example.demo.repository

import com.example.demo.models.Account
import com.example.demo.repository.mappers.AccountRowMapper
import org.apache.catalina.core.ApplicationContext
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Repository

import java.sql.SQLException

@Repository
class AccountsRepository {

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate

    private AccountRowMapper mapper = new AccountRowMapper()

    Account findById(Long id) {
        
        def params = [id: id]
        def query = SqlQueries.GET_ACCOUNT
        query += "WHERE ID = :id"

        try{
            jdbcTemplate.queryForObject(query, params, new AccountRowMapper()) as Account
        } catch (SQLException e) {
            throw new SQLException(e)
        }
    }

    Long registerAccount(Account account) {
        
        
        def params = [username: account.credentials.username, 
                      password: account.credentials.password, 
                      name: account.name, 
                      lastname: account.lastname, 
                      money: account.money]
        def query = SqlQueries.CREATE_ACCOUNT

        try{
            jdbcTemplate.update(query, params)
        } catch (SQLException e) {
            throw new SQLException(e)
        }
    }
}
