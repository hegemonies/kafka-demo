package com.hegemonies.kafkademo.dto.ticket

import com.hegemonies.kafkademo.dto.TicketDto

data class GetTicketsResponse(
    val tickets: List<TicketDto>
)
