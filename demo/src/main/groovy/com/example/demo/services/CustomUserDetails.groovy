package com.example.demo.services

import com.example.demo.models.Role
import com.example.demo.models.User
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class CustomUserDetails implements UserDetails {
    
    private Collection<? extends GrantedAuthority> authorities
    private String password
    private String username

    CustomUserDetails(User user) {
        this.username = user.username
        this.password = user.password
        this.authorities = translate(user.roles)
    }

    private Collection<? extends GrantedAuthority> translate(Set<Role> roles) {
        List<GrantedAuthority> authorities = []
        roles.forEach {
            String name = it.name.toUpperCase()
            //Make sure that all roles start with "ROLE_"
            if (!name.startsWith("ROLE_"))
                name = "ROLE_" + name
            authorities << new SimpleGrantedAuthority(name)
        }
        authorities
    }
    
    @Override
    Collection<? extends GrantedAuthority> getAuthorities() {
        authorities
    }

    @Override
    String getPassword() {
        password
    }

    @Override
    String getUsername() {
        username
    }

    @Override
    boolean isAccountNonExpired() {
        return false
    }

    @Override
    boolean isAccountNonLocked() {
        return false
    }

    @Override
    boolean isCredentialsNonExpired() {
        return false
    }

    @Override
    boolean isEnabled() {
        return false
    }
}
