package com.wafflestudio.seminar.domain.user

import com.wafflestudio.seminar.domain.user.dto.UserDto
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder
) {
    fun signup(signupRequest: UserDto.SignupRequest): User {
        val encodedPassword = passwordEncoder.encode(signupRequest.password)
        return userRepository.save(User(signupRequest.email, encodedPassword))
    }
}