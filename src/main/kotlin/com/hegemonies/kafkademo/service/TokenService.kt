package com.hegemonies.kafkademo.service

import arrow.core.Either
import arrow.core.getOrHandle
import com.hegemonies.kafkademo.config.properties.TokenProperties
import com.hegemonies.kafkademo.dto.ErrorResult
import com.hegemonies.kafkademo.mapper.toEither
import com.hegemonies.kafkademo.mapper.toErrorResult
import com.hegemonies.kafkademo.model.Token
import com.hegemonies.kafkademo.repository.TokenRepository
import kotlinx.coroutines.reactive.awaitFirst
import org.springframework.stereotype.Service
import java.util.*

@Service
class TokenService(
    private val tokenProperties: TokenProperties,
    private val tokenRepository: TokenRepository,
) {

    fun isTokenExpired(token: Token): Boolean {
        val currentTimestamp = System.currentTimeMillis()
        return currentTimestamp - token.createdAt > tokenProperties.lifetime.toMillis()
    }

    suspend fun createToken(userId: Long): Either<ErrorResult, Token> {
        val currentTimestamp = System.currentTimeMillis()
        val token = Either.catch {
            tokenRepository.findByUserId(userId).awaitFirst()
        }.getOrHandle { error ->
            return error.toErrorResult().toEither()
        }

        return if (token == null) {
            val newToken = Either.catch {
                tokenRepository.save(
                    Token(
                        userId = userId,
                        createdAt = currentTimestamp,
                        token = UUID.randomUUID().toString()
                    )
                ).awaitFirst()
            }.getOrHandle { error ->
                return error.toErrorResult().toEither()
            }

            Either.Right(newToken)
        } else {
            if (isTokenExpired(token)) {
                Either.catch {
                    tokenRepository.updateCreatedAtByUserId(
                        userId = userId,
                        createdAt = currentTimestamp
                    )
                }.getOrHandle { error ->
                    return error.toErrorResult().toEither()
                }
            }

            Either.Right(token)
        }
    }
}
