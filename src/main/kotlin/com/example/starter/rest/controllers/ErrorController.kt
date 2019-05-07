package com.example.starter.rest.controllers

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController

@RestController
class ErrorController {
    @RequestMapping(value = ["/denied"], method = [RequestMethod.GET])
    fun denied(): ResponseEntity<Void> {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build<Void>()
    }
}
