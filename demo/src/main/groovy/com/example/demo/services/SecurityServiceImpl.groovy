package com.example.demo.services

import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Slf4j
@Service
class SecurityServiceImpl implements SecurityService {

    //@Autowired
    private AuthenticationManager authenticationManager
    
    @Autowired
    private UserDetailsService userDetailsService
    

    @Override
    String findLoggedInUsername() {
        def userDetails = SecurityContextHolder.context.authentication.details
        
        userDetails instanceof UserDetails ? userDetails.username : null
    }

    @Override
    void autologin(String username, String password) {
        def userDetails = userDetailsService.loadUserByUsername(username)
        def usernamePasswordAuthToken = new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.authorities)
        
        authenticationManager.authenticate(usernamePasswordAuthToken)
        
        if(usernamePasswordAuthToken.isAuthenticated()) {
            SecurityContextHolder.context.setAuthentication(usernamePasswordAuthToken)
            log.info("Autologin as [$username] complete!")
        }
    }
}
