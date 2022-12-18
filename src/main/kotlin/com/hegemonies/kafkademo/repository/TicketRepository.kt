package com.hegemonies.kafkademo.repository

import com.hegemonies.kafkademo.model.Ticket
import org.springframework.data.r2dbc.repository.R2dbcRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Repository
interface TicketRepository : R2dbcRepository<Ticket, Long> {

    fun findTopByCreatedAtAfterAndType(createdAt: Long, type: Short): Mono<Ticket>

    fun findAllByCreatedAtAfterAndClosedAtNotNull(createdAt: Long): Flux<Ticket>
}
