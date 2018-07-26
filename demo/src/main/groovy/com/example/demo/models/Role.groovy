package com.example.demo.models

import javax.persistence.ElementCollection
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.ManyToMany
import javax.persistence.Table

@Entity
@Table(name = 'role')
class Role {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id
    
    String name

    @ElementCollection(targetClass=User.class)
    Set<User> users

    @ManyToMany(mappedBy = "roles")
    Set<User> getUsers() {
        users
    }
    
}
