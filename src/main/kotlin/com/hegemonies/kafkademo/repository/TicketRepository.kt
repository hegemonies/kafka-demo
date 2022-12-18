package com.hegemonies.kafkademo.repository

import com.hegemonies.kafkademo.consts.TableNames
import com.hegemonies.kafkademo.model.Ticket
import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.r2dbc.repository.R2dbcRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Repository
interface TicketRepository : R2dbcRepository<Ticket, Long> {

    @Query(
        """
            SELECT id, ticket_number, type, created_at, closed_at FROM ${TableNames.TICKETS}
                WHERE created_at >= ${'$'}1 AND type = ${'$'}2
                ORDER BY id DESC
                LIMIT 1
        """
    )
    fun findTopByCreatedAtAfterAndType(createdAt: Long, type: Short): Mono<Ticket>

    @Query(
        """
            SELECT id, ticket_number, type, created_at, closed_at FROM ${TableNames.TICKETS}
                WHERE created_at >= ${'$'}1 AND closed_at IS NULL
                ORDER BY id DESC
        """
    )
    fun findAllByCreatedAtAfterAndClosedAtNotNull(createdAt: Long): Flux<Ticket>
}
