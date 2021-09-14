package com.wafflestudio.seminar.domain.user.api

import com.wafflestudio.seminar.domain.user.UserService
import com.wafflestudio.seminar.domain.user.dto.UserDto
import com.wafflestudio.seminar.global.auth.JwtTokenProvider
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletResponse
import javax.validation.Valid

@RestController
@RequestMapping("/api/v1/users")
class UserController(
    private val userService: UserService,
    private val jwtTokenProvider: JwtTokenProvider
) {
    @PostMapping("/")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun signup(@RequestBody @Valid signupRequest: UserDto.SignupRequest, response: HttpServletResponse): UserDto.Response {
        val user = userService.signup(signupRequest)
        response.addHeader("Authentication", jwtTokenProvider.generateToken(user.email))
        return UserDto.Response(user)
    }
}