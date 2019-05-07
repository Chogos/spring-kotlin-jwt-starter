package com.example.starter.rest.dtos

data class PasswordChangeRequest(
        val oldPassword: String,
        val newPassword: String
)
