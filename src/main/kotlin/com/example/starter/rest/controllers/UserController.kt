package com.example.starter.rest.controllers

import com.example.starter.rest.dtos.PasswordChangeRequest
import com.example.starter.rest.models.User
import com.example.starter.rest.services.UserService
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(value = ["/api/user"], produces = [MediaType.APPLICATION_JSON_VALUE])
class UserController(
        private val userService: UserService
) {
    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = ["/{userId}"], method = [RequestMethod.GET])
    fun getById(@PathVariable userId: Long?): ResponseEntity<User> {
        return ResponseEntity.of(userService.findById(userId as Long))
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = ["/list"], method = [RequestMethod.GET])
    fun list(): ResponseEntity<List<User>> {
        return ResponseEntity.ok(userService.findAll())
    }

    @RequestMapping(value = ["/changePassword"], method = [RequestMethod.POST])
    fun changePassword(@RequestBody request: PasswordChangeRequest): ResponseEntity<Void> {
        userService.changePassword(request)
        return ResponseEntity.ok().build()
    }
}
