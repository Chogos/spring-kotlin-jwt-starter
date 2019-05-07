package com.example.starter.security

import com.example.starter.rest.models.User
import com.example.starter.rest.services.UserService
import com.example.starter.utils.exceptions.AuthenticationException
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.time.Instant
import java.util.*
import javax.servlet.http.HttpServletRequest

@Component
class SecurityUtils(
        private val userService: UserService
) {
    @Value("%{app.name}")       private val appName: String = ""
    @Value("%{jwt.secret}")     private var secret: String = ""
    @Value("%{jwt.expires_in}") private val tokenExpiration: Int = 0
    @Value("%{jwt.header}")     private val authHeader: String = ""

    companion object {
        @JvmField val signatureAlgorithm = SignatureAlgorithm.HS512
    }

    fun getUsernameFromToken(token: String): String? = this.getClaimsFromToken(token).subject
    fun getIssuedAtDateFromToken(token: String): Date? = this.getClaimsFromToken(token).issuedAt

    fun generateRefreshedToken(token: String): String? {
        val claims = this.getClaimsFromToken(token)
        claims.issuedAt = Date.from(Instant.now())
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(generateExpirationDate())
                .signWith(signatureAlgorithm, secret)
                .compact()
    }

    fun generateToken(username: String): String {
        return Jwts.builder()
                .setIssuer(appName)
                .setSubject(username)
                .setIssuedAt(Date.from(Instant.now()))
                .setExpiration(generateExpirationDate())
                .signWith(signatureAlgorithm, secret)
                .compact()
    }

    private fun getClaimsFromToken(token: String): Claims {
        return try {
            Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .body
        } catch (e: Exception)  {
            when (e) {
                is IllegalArgumentException ->
                    throw AuthenticationException("The claims string is either empty or null ", e)
                else ->
                    throw AuthenticationException("Couldn't process claims from authentication token", e)
            }
        }
    }

    fun generateExpirationDate(): Date {
        return Date.from(Instant.now().plusSeconds(tokenExpiration.toLong()))
    }

    fun validateToken(token: String): Boolean {
        val username = getUsernameFromToken(token) ?:
            throw AuthenticationException("Couldn't extract username from authentication token")
        val user = userService.loadUserByUsername(username) as User
        val created = getIssuedAtDateFromToken(token)
        return  username == user.username && created!!.after(user.lastPasswordChangeDate)
    }

    fun getToken(request: HttpServletRequest): String {
        val authHeader = request.getHeader(authHeader)
        return if (authHeader != null && authHeader.startsWith("Bearer "))
            authHeader.substring(7)
        else
            throw AuthenticationException("Authentication token not found in request header")
    }
}
