package com.wafflestudio.seminar.domain.user

import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository: JpaRepository<User, Long?> {
    fun findByUsername(username: String): User?
}
