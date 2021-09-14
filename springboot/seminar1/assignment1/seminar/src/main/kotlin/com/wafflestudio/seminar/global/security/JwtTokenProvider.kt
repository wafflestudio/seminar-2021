package com.wafflestudio.seminar.global.security

import com.wafflestudio.seminar.domain.user.UserRepository
import com.wafflestudio.seminar.global.security.model.UserPrincipal
import io.jsonwebtoken.*
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Component
import java.util.*

@Component
class JwtTokenProvider(private val userRepository: UserRepository) {
    val tokenPrefix = "Bearer "
    val headerString = "Authentication"

    @Value("\${app.jwt.jwt-secret-key}")
    private val jwtSecretKey: String? = null

    @Value("\${app.jwt.jwt-expiration-in-ms}")
    private val jwtExpirationInMs: Long? = null

    // Generate jwt token with prefix
    fun generateToken(authentication: Authentication): String {
        val userPrincipal = authentication.principal as UserPrincipal
        return generateToken(userPrincipal.user.username)
    }

    fun generateToken(username: String): String {
        val claims: MutableMap<String, Any> = HashMap()
        claims["username"] = username
        val now = Date()
        val expiryDate = Date(now.time + jwtExpirationInMs!!)
        return tokenPrefix + Jwts.builder()
            .setClaims(claims)
            .setIssuedAt(now)
            .setExpiration(expiryDate)
            .signWith(SignatureAlgorithm.HS256, jwtSecretKey)
            .compact()
    }

    fun validateToken(authToken: String?): Boolean {
        val authTokenWithoutPrefix = removePrefix(authToken ?: return false)
        if (!authTokenWithoutPrefix.startsWith(tokenPrefix)) {
            logger.error("Token not match type Bearer")
            return false
        }
        try {
            Jwts.parser().setSigningKey(jwtSecretKey).parseClaimsJws(authTokenWithoutPrefix)
            return true
        } catch (ex: SignatureException) {
            logger.error("Invalid JWT signature")
        } catch (ex: MalformedJwtException) {
            logger.error("Invalid JWT token")
        } catch (ex: ExpiredJwtException) {
            logger.error("Expired JWT token")
        } catch (ex: UnsupportedJwtException) {
            logger.error("Unsupported JWT token")
        } catch (ex: IllegalArgumentException) {
            logger.error("JWT claims string is empty.")
        }
        return false
    }

    fun removePrefix(tokenWithPrefix: String): String {
        return tokenWithPrefix.replace(tokenPrefix, "").trim { it <= ' ' }
    }

    companion object {
        private val logger = LoggerFactory.getLogger(JwtTokenProvider::class.java)
    }
}
