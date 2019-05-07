package com.example.starter.rest.services

import com.example.starter.rest.dtos.PasswordChangeRequest
import com.example.starter.rest.models.User
import com.example.starter.rest.repositories.UserRepository
import com.example.starter.utils.exceptions.NotFoundException
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Lazy
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.util.*

@Service
class UserService(
        private val userRepository: UserRepository,
        @Lazy private val passwordEncoder: PasswordEncoder,
        @Lazy private val authenticationManager: AuthenticationManager
) : UserDetailsService {

    val logger: Logger = LoggerFactory.getLogger(UserDetailsService::class.java)

    override fun loadUserByUsername(username: String): UserDetails = findByUsername(username).orElseThrow {
        NotFoundException("User with username: '$username' was not found")
    }

    fun changePassword(passwordChangeRequest: PasswordChangeRequest) {
        val currentUser = SecurityContextHolder.getContext().authentication
        val username = currentUser.name
        logger.debug("Changing password for user: '$username'")

        val (oldPassword, newPassword) = passwordChangeRequest
        authenticationManager.authenticate(UsernamePasswordAuthenticationToken(username, oldPassword))
        val user = loadUserByUsername(username) as User
        user.password = passwordEncoder.encode(newPassword)
        userRepository.save(user)
    }

    fun findByUsername(username: String): Optional<User> {
        return userRepository.findByUsername(username)
    }

    fun findById(id:Long): Optional<User> {
        return userRepository.findById(id)
    }

    fun findAll(): List<User> {
        return userRepository.findAll()
    }
}
