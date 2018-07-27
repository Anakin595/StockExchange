package com.example.demo.services

import com.example.demo.models.User
import com.example.demo.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Component

@Component
class MongoUserDetailsService implements UserDetailsService {

    @Bean
    BCryptPasswordEncoder passwordEncoder() {
        new BCryptPasswordEncoder()
    }
    
    @Autowired
    private UserRepository repository
    
    @Override
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = repository.findByUsername(username)
        if(user == null) {
            throw new UsernameNotFoundException('User not found')
        }
        def authorities = [new SimpleGrantedAuthority('user')]
        new org.springframework.security.core.userdetails.User(user.username, passwordEncoder().encode(user.password), authorities)
    }
    
}
