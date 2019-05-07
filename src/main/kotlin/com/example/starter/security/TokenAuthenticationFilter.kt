package com.example.starter.security

import com.example.starter.rest.services.UserService
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class TokenAuthenticationFilter(
        private val userService: UserService,
        private val securityUtils: SecurityUtils
) : OncePerRequestFilter() {
    override fun doFilterInternal(request: HttpServletRequest,
                                  response: HttpServletResponse,
                                  filterChain: FilterChain) {
        val authToken = securityUtils.getToken(request)
        if (securityUtils.validateToken(authToken)) {
            val username = securityUtils.getUsernameFromToken(authToken)
            val userDetails = userService.loadUserByUsername(username as String)
            val authentication = AuthenticationToken(authToken, userDetails)
            authentication.isAuthenticated = true
            SecurityContextHolder.getContext().authentication = authentication
        }

        filterChain.doFilter(request, response)
    }
}
