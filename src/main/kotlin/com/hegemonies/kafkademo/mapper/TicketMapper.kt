package com.hegemonies.kafkademo.mapper

import com.hegemonies.kafkademo.dto.TicketDto
import com.hegemonies.kafkademo.model.Ticket

fun Ticket.toDto(): TicketDto =
    TicketDto(
        ticketNumber = this.ticketNumber
    )
