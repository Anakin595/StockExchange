package com.example.demo.models

import javax.persistence.ElementCollection
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.JoinTable
import javax.persistence.OneToMany
import javax.persistence.Table
import javax.persistence.Transient

@Entity
@Table(name = "user")
class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id
    
    String username
    
    String password
    
    @Transient
    String passwordConfirm

    @ElementCollection(targetClass=Role.class)
    Set<Role> roles

    @OneToMany
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    Set<Role> getRoles() {
        roles
    }
    
    
}
