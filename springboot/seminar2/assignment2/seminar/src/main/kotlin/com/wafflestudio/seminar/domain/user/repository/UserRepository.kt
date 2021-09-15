package com.wafflestudio.seminar.domain.user.repository

import com.wafflestudio.seminar.domain.user.model.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository: JpaRepository<User, Long?> {
    fun findByEmail(email: String): User?
    fun existsByEmail(email: String): Boolean
}
