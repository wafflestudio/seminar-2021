package com.wafflestudio.seminar.global.auth

import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class JwtAuthorizationFilter(
    authenticationManager: AuthenticationManager?,
    private val jwtTokenProvider: JwtTokenProvider
) : BasicAuthenticationFilter(authenticationManager) {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val token = request.getHeader(jwtTokenProvider.headerString)
        if (token == null || !jwtTokenProvider.validateToken(token)) {
            filterChain.doFilter(request, response)
            return
        }
        val authentication = jwtTokenProvider.getAuthenticationTokenFromJwt(token)
        SecurityContextHolder.getContext().authentication = authentication
        filterChain.doFilter(request, response)
    }
}
