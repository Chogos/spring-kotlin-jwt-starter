package com.example.starter.rest.services

import com.example.starter.rest.dtos.auth.JwtAuthenticationRequest
import com.example.starter.rest.dtos.auth.JwtToken
import com.example.starter.rest.models.User
import com.example.starter.security.SecurityUtils
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import javax.servlet.http.HttpServletRequest

@Service
class AuthenticationService(
        private val securityUtils: SecurityUtils,
        private val authenticationManager: AuthenticationManager
) {
    fun authenticate(authenticationRequest: JwtAuthenticationRequest): JwtToken {
        val authentication = authenticationManager.authenticate(
                UsernamePasswordAuthenticationToken(
                        authenticationRequest.username,
                        authenticationRequest.password
                )
        )

        SecurityContextHolder.getContext().authentication = authentication

        val user = authentication.principal as User
        val jws = securityUtils.generateToken(user.username)
        val expiresIn = securityUtils.generateExpirationDate()
        return JwtToken(jws, expiresIn)
    }

    fun refreshToken(request: HttpServletRequest): JwtToken {
        val authToken = securityUtils.getToken(request)
        securityUtils.validateToken(authToken)
        val refreshedToken = securityUtils.generateRefreshedToken(authToken) as String
        val expiresIn = securityUtils.generateExpirationDate()
        return JwtToken(refreshedToken, expiresIn)
    }
}
