package com.example.demo.repository

class SqlQueries {

    static final String GET_ACCOUNT = """ 
                SELECT ID, USERNAME, NAME, LASTNAME, MONEY FROM ACCOUNTS """

    static final String CREATE_ACCOUNT = """ 
                INSERT INTO accounts(username, password, name, lastname, money) VALUES(:username, :password, :name, :lastname, :money) """

}
