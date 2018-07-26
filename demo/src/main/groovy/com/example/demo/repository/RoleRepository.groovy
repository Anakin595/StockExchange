package com.example.demo.repository

import com.example.demo.models.Role
import org.springframework.data.jpa.repository.JpaRepository

interface RoleRepository extends JpaRepository<Role, Long>{
}
