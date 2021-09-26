package com.wafflestudio.seminar.global.security

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.wafflestudio.seminar.global.security.dto.LoginRequest
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.security.web.util.matcher.AntPathRequestMatcher
import java.io.BufferedReader
import java.io.IOException
import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class GeneralAuthenticationFilter(authenticationManager: AuthenticationManager?, jwtTokenProvider: JwtTokenProvider) :
    UsernamePasswordAuthenticationFilter() {
    private val jwtTokenProvider: JwtTokenProvider
    override fun successfulAuthentication(
        request: HttpServletRequest,
        response: HttpServletResponse,
        chain: FilterChain,
        authResult: Authentication
    ) {
        response.addHeader("Authentication", jwtTokenProvider.generateToken(authResult))
        response.status = HttpServletResponse.SC_NO_CONTENT
    }

    override fun unsuccessfulAuthentication(
        request: HttpServletRequest,
        response: HttpServletResponse,
        failed: AuthenticationException
    ) {
        super.unsuccessfulAuthentication(request, response, failed);
        response.status = HttpServletResponse.SC_UNAUTHORIZED
    }

    override fun attemptAuthentication(request: HttpServletRequest, response: HttpServletResponse): Authentication {
        // Parse auth request
        val parsedRequest: LoginRequest = try {
            parseRequest(request)
        } catch (e: IOException) {
            throw RuntimeException("Bad request")
        }
        val authRequest: Authentication = UsernamePasswordAuthenticationToken(parsedRequest.email, parsedRequest.password)
        return authenticationManager.authenticate(authRequest)
    }

    private fun parseRequest(request: HttpServletRequest): LoginRequest {
        val reader: BufferedReader = request.reader
        val objectMapper = ObjectMapper()
        return objectMapper.readValue(reader)
    }

    init {
        this.authenticationManager = authenticationManager
        setRequiresAuthenticationRequestMatcher(AntPathRequestMatcher("/api/v1/user/signin/", "POST"))
        this.jwtTokenProvider = jwtTokenProvider
    }
}
