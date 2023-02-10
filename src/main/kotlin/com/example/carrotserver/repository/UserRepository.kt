package com.example.carrotserver.repository

import com.example.carrotserver.domain.entity.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository: JpaRepository<User, Long> {
    fun findByMail(email: String): User?
}