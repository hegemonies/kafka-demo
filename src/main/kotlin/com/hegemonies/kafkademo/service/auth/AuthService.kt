package com.hegemonies.kafkademo.service.auth

import arrow.core.Either
import arrow.core.getOrHandle
import com.hegemonies.kafkademo.dto.ErrorResult
import com.hegemonies.kafkademo.mapper.toEither
import com.hegemonies.kafkademo.mapper.toErrorResult
import com.hegemonies.kafkademo.model.User
import com.hegemonies.kafkademo.repository.TokenRepository
import com.hegemonies.kafkademo.repository.UserRepository
import com.hegemonies.kafkademo.service.SecurityService
import com.hegemonies.kafkademo.service.TokenService
import kotlinx.coroutines.reactive.awaitFirstOrNull
import org.springframework.stereotype.Service

@Service
class AuthService(
    private val userRepository: UserRepository,
    private val securityService: SecurityService,
    private val tokenService: TokenService,
    private val tokenRepository: TokenRepository,
) : IAuthService {

    override suspend fun registration(
        username: String,
        password: String,
        passwordAgain: String
    ): Either<ErrorResult, Unit> {
        if (password != passwordAgain) {
            return Either.Left(
                ErrorResult(
                    message = "Password is not equal to password again",
                    code = 400
                )
            )
        }

        val user = userRepository.findByUsername(username).awaitFirstOrNull()

        if (user != null) {
            return Either.Left(
                ErrorResult(
                    message = "User with such name is already exists",
                    code = 400
                )
            )
        } else {
            val encodedPassword = securityService.encodePassword(password)
            userRepository.save(
                User(
                    username = username,
                    password = encodedPassword
                )
            ).awaitFirstOrNull()
        }

        return Either.Right(Unit)
    }

    override suspend fun auth(username: String, password: String): Either<ErrorResult, String> {
        val user = userRepository.findByUsername(username).awaitFirstOrNull()
            ?: return Either.Left(
                ErrorResult(
                    message = "User with such name does not exist",
                    code = 400
                )
            )

        val encodedPassword = securityService.encodePassword(password)

        return if (user.password == encodedPassword) {
            val token = tokenService.createToken(user.id!!).getOrHandle { error ->
                return error.toEither()
            }

            Either.Right(token.token)
        } else {
            ErrorResult(
                message = "Invalid password",
                code = 400
            ).toEither()
        }
    }

    override suspend fun checkToken(authToken: String): Either<ErrorResult, Boolean> {
        val token = Either.catch {
            tokenRepository.findByToken(authToken).awaitFirstOrNull()
        }.getOrHandle { error ->
            return error.toErrorResult().toEither()
        }

        return Either.Right(token != null)
    }
}
