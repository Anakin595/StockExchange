package com.example.demo.repository.mappers

import com.example.demo.models.Account
import org.springframework.jdbc.core.RowMapper

import java.sql.ResultSet
import java.sql.SQLException

class AccountRowMapper implements RowMapper<Account> {
    @Override
    Account mapRow(ResultSet rs, int rowNum) throws SQLException {
        new Account(
                id: rs.getInt("id"),
                username: rs.getString('username'),
                name: rs.getString('name'),
                lastname: rs.getString('lastname'),
                money: rs.getFloat('money')
        )
    }
}
