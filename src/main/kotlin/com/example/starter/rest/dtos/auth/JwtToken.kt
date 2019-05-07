package com.example.starter.rest.dtos.auth

import java.util.*

data class JwtToken(
        val token: String,
        val validUntil: Date
)
