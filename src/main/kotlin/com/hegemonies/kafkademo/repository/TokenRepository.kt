package com.hegemonies.kafkademo.repository

import com.hegemonies.kafkademo.model.Token
import org.springframework.data.r2dbc.repository.Modifying
import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.r2dbc.repository.R2dbcRepository
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import reactor.core.publisher.Mono

@Repository
interface TokenRepository : R2dbcRepository<Token, Long> {

    fun findByUserId(userId: Long): Mono<Token>

    fun findByToken(token: String): Mono<Token>

    @Transactional
    @Modifying
    @Query(
        """
            UPDATE Token t
                SET t.createdAt = :createdAt
            WHERE t.userId = :userId
        """
    )
    fun updateCreatedAtByUserId(userId: Long, createdAt: Long): Int
}
