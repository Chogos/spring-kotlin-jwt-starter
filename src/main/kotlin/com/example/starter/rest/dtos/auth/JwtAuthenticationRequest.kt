package com.example.starter.rest.dtos.auth

data class JwtAuthenticationRequest(
        val username: String,
        val password: String
)
