package com.hegemonies.kafkademo.controller

import arrow.core.Either
import arrow.core.getOrHandle
import com.hegemonies.kafkademo.dto.auth.AuthRequest
import com.hegemonies.kafkademo.dto.auth.AuthResponse
import com.hegemonies.kafkademo.dto.registration.RegistrationRequest
import com.hegemonies.kafkademo.service.auth.IAuthService
import org.slf4j.LoggerFactory
import org.springframework.http.client.ClientHttpRequestExecution
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class AuthController(
    private val authService: IAuthService,
) {

    @PostMapping("/auth")
    suspend fun auth(@RequestBody request: AuthRequest): AuthResponse {

        logger.debug("Receive /auth request: $request")

        val authResult = authService.auth(
            username = request.username,
            password = request.password
        )

        when (authResult) {
            is Either.Left -> throw authResult.value
            is Either.Right -> return AuthResponse(token = authResult.value).also {
                logger.debug("Success handle of /auth request: $request")
            }
        }
    }

    @PostMapping("/registration")
    suspend fun registration(@RequestBody request: RegistrationRequest) {
        logger.debug("Receive /registration request: $request")

        authService.registration(
            username = request.username,
            password = request.password,
            passwordAgain = request.passwordAgain
        ).getOrHandle { error ->
            throw error
        }

        logger.debug("Success handle of /registration request: $request")
    }

    companion object {
        private val logger = LoggerFactory.getLogger(this::class.java.declaringClass)
    }
}
