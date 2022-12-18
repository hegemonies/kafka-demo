package com.hegemonies.kafkademo.dto.ticket

import com.hegemonies.kafkademo.dto.TicketDto
import reactor.core.publisher.Flux

data class GetTicketsResponse(
    val tickets: Flux<TicketDto>
)
