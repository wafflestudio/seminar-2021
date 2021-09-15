package com.wafflestudio.seminar.domain.user.api

import com.wafflestudio.seminar.domain.user.User
import com.wafflestudio.seminar.domain.user.UserService
import com.wafflestudio.seminar.domain.user.dto.UserDto
import com.wafflestudio.seminar.global.auth.CurrentUser
import com.wafflestudio.seminar.global.auth.JwtTokenProvider
import org.springframework.http.HttpEntity
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.function.EntityResponse
import javax.servlet.http.HttpServletResponse
import javax.validation.Valid

@RestController
@RequestMapping("/api/v1/users")
class UserController(
    private val userService: UserService,
    private val jwtTokenProvider: JwtTokenProvider
) {
    @PostMapping("/")
    fun signup(@Valid @RequestBody signupRequest: UserDto.SignupRequest): ResponseEntity<UserDto.Response> {
        val user = userService.signup(signupRequest)
        return ResponseEntity.noContent().header("Authentication", jwtTokenProvider.generateToken(user.email)).build()
    }

    @GetMapping("/me/")
    fun getCurrentUser(@CurrentUser user: User): UserDto.Response {
        return UserDto.Response(user)
    }
}
