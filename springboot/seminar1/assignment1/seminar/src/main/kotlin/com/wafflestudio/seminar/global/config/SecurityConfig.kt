package com.wafflestudio.seminar.global.config

import com.wafflestudio.seminar.global.security.GeneralAuthenticationFilter
import com.wafflestudio.seminar.global.security.JwtAuthenticationEntryPoint
import com.wafflestudio.seminar.global.security.JwtTokenProvider
import com.wafflestudio.seminar.global.security.model.UserPrincipalDetailService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
class SecurityConfig(private val jwtAuthenticationEntryPoint: JwtAuthenticationEntryPoint,
                     private val jwtTokenProvider: JwtTokenProvider,
                     private val userPrincipalDetailService: UserPrincipalDetailService
) : WebSecurityConfigurerAdapter() {
    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.authenticationProvider(daoAuthenticationProvider())
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return Argon2PasswordEncoder()
//        return BCryptPasswordEncoder()
    }

    @Bean
    fun daoAuthenticationProvider(): DaoAuthenticationProvider {
        val provider = DaoAuthenticationProvider()
        provider.setPasswordEncoder(passwordEncoder())
        provider.setUserDetailsService(userPrincipalDetailService)
        return provider
    }

    override fun configure(http: HttpSecurity) {
        http
            .csrf().disable()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint)
            .and()
            .addFilter(GeneralAuthenticationFilter(authenticationManager(), jwtTokenProvider))
            .authorizeRequests()
            .antMatchers("/api/v1/user/signin/").permitAll()  // Auth entrypoint
            .antMatchers("/api/v1/user/signup/").permitAll()  // SignUp user
            .anyRequest().authenticated()
    }

}
