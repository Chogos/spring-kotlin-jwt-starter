package com.example.starter.rest.controllers

import com.example.starter.rest.dtos.auth.JwtAuthenticationRequest
import com.example.starter.rest.dtos.auth.JwtToken
import com.example.starter.rest.services.AuthenticationService
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.HttpServletRequest

@RestController
@RequestMapping(value = ["/api/auth"], produces = [MediaType.APPLICATION_JSON_VALUE])
class AuthenticationController(
        private val authenticationService: AuthenticationService
) {
    @RequestMapping(value = ["/login"], method = [RequestMethod.POST])
    fun createAuthenticationToken(@RequestBody authenticationRequest: JwtAuthenticationRequest)
            : ResponseEntity<JwtToken> {
        return ResponseEntity.ok<JwtToken>(authenticationService.authenticate(authenticationRequest))
    }

    @RequestMapping(value = ["/refresh"], method = [RequestMethod.POST])
    fun refreshToken(request: HttpServletRequest): ResponseEntity<JwtToken> {
        return ResponseEntity.ok<JwtToken>(authenticationService.refreshToken(request))
    }
}
