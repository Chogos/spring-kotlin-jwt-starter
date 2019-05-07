package com.example.starter.utils.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.UNAUTHORIZED)
class AuthenticationException : RuntimeException {
    constructor(message: String, cause: Throwable?): super(message, cause)
    constructor(message: String): super(message)
}
