package com.example.demo.services

import com.example.demo.models.User
import com.example.demo.repository.RoleRepository
import com.example.demo.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserServiceImpl implements UserService {
    
    private UserRepository userRepository
    
    private RoleRepository roleRepository
    
    private BCryptPasswordEncoder bCryptPasswordEncoder

    @Autowired
    UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository
        this.roleRepository = roleRepository
        this.bCryptPasswordEncoder = bCryptPasswordEncoder
    }

    @Override
    void save(User user) {
        user.password = bCryptPasswordEncoder.encode(user.password)
        user.roles = roleRepository.findAll()
        userRepository.save(user)
    }

    @Override
    User findByUsername(String username) {
        userRepository.findByUsername(username)
    }
}
