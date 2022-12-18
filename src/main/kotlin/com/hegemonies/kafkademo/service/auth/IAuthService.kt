package com.hegemonies.kafkademo.service.auth

import arrow.core.Either
import com.hegemonies.kafkademo.dto.ErrorResult

interface IAuthService {

    /**
     * Registration of a service manager in the system.
     */
    suspend fun registration(username: String, password: String, passwordAgain: String): Either<ErrorResult, Unit>

    /**
     * Auth a service manager in the system.
     */
    suspend fun auth(username: String, password: String): Either<ErrorResult, String>

    /**
     * Validate auth token.
     */
    suspend fun checkToken(authToken: String): Either<ErrorResult, Boolean>
}
