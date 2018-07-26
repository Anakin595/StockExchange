package com.example.demo.configuration

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy

@Configuration
@EnableWebSecurity
class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String[] UNSECURED_ENDPOINTS = [
            "/h2-console",
            "/h2-console/**",
            "/account/**"
    ]

    @Override
    protected void configure(HttpSecurity security) throws Exception
    {
        security.csrf().disable().authorizeRequests()
                .antMatchers(UNSECURED_ENDPOINTS).permitAll()
                .anyRequest().authenticated()
                .and()
                .logout()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        security.httpBasic().disable()
        security.headers().frameOptions().disable()
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("user").password("user").roles("USER").and()
                .withUser("admin").password("admin").roles("USER","ADMIN")

    }

}
