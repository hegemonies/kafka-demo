package com.hegemonies.kafkademo.service.queue

import arrow.core.Either
import com.hegemonies.kafkademo.dto.ErrorResult
import com.hegemonies.kafkademo.dto.TicketDto
import com.hegemonies.kafkademo.dto.TicketType
import reactor.core.publisher.Flux

interface IQueueService {

    /**
     * Returns list of active tickets.
     */
    suspend fun getActiveTickets(): Either<ErrorResult, Flux<TicketDto>>

    /**
     * Creates new tickets in queue.
     */
    suspend fun createTicket(ticketType: TicketType): Either<ErrorResult, Long>

    /**
     * Manager gets ticket for service.
     */
    suspend fun getTicket(ticketNumber: Long, authToken: String): Either<ErrorResult, Long>
}
