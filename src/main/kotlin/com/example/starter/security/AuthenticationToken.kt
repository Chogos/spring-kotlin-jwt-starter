package com.example.starter.security

import org.springframework.security.authentication.AbstractAuthenticationToken
import org.springframework.security.core.userdetails.UserDetails

class AuthenticationToken(
        private val token: String,
        private val principal: UserDetails
) : AbstractAuthenticationToken(principal.authorities) {
    override fun getCredentials(): String = token
    override fun getPrincipal(): UserDetails = principal
}
