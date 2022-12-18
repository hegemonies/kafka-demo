package com.hegemonies.kafkademo.repository

import com.hegemonies.kafkademo.model.User
import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.r2dbc.repository.R2dbcRepository
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono

@Repository
interface UserRepository : R2dbcRepository<User, Long> {

    @Query(
        """
            SELECT id, username, password FROM users WHERE username = ${'$'}1
        """
    )
    fun findByUsername(username: String): Mono<User>
}
