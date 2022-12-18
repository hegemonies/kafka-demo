package com.hegemonies.kafkademo.repository

import com.hegemonies.kafkademo.consts.TableNames
import com.hegemonies.kafkademo.model.Token
import org.springframework.data.r2dbc.repository.Modifying
import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.r2dbc.repository.R2dbcRepository
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import reactor.core.publisher.Mono

@Repository
interface TokenRepository : R2dbcRepository<Token, Long> {

    @Query(
        """
            SELECT id, user_id, created_at, token FROM ${TableNames.TOKENS}
                WHERE user_id = ${'$'}1
        """
    )
    fun findByUserId(userId: Long): Mono<Token>

    @Query(
        """
            SELECT id, user_id, created_at, token FROM ${TableNames.TOKENS}
                WHERE token = ${'$'}1
        """
    )
    fun findByToken(token: String): Mono<Token>

    @Transactional
    @Modifying
    @Query(
        """
            UPDATE ${TableNames.TOKENS}
                SET created_at = ${'$'}2
            WHERE user_id = ${'$'}1
        """
    )
    fun updateCreatedAtByUserId(userId: Long, createdAt: Long): Mono<Int>
}
