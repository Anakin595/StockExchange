package com.example.demo.configuration

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jdbc.datasource.DriverManagerDataSource

import javax.sql.DataSource

@Configuration
class DataSourceConfig {

    @Value('${spring.datasource.url}')
    private String url
    
    @Value('${spring.datasource.driverClassName}')
    private String driverClassName
    
    @Value('${spring.datasource.username}')
    private String username
    
    @Value('${spring.datasource.password}')
    private String password
    
    @Bean
    DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource()
        dataSource.setDriverClassName(driverClassName)
        dataSource.setUrl(url)
        dataSource.setUsername(username)
        dataSource.setPassword(password)
        dataSource as DataSource
    }
    
    // http://www.baeldung.com/rest-api-spring-oauth2-angularjs
    
    
}
