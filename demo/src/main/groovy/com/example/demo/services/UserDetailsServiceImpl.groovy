package com.example.demo.services

import com.example.demo.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User as SpringUser
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserDetailsServiceImpl implements UserDetailsService {
    
    @Autowired
    UserRepository userRepository

    @Override
    @Transactional(readOnly = true)
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        def user = userRepository.findByUsername(username)
        
        def grantedAuth = []
        user.roles.forEach {
            grantedAuth << new SimpleGrantedAuthority(it.name)
        }
        new SpringUser(user.username, user.password, grantedAuth)
    }
}
