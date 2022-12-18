package com.hegemonies.kafkademo.controller

import arrow.core.getOrHandle
import com.hegemonies.kafkademo.consts.HttpHeaderNames
import com.hegemonies.kafkademo.dto.TicketType
import com.hegemonies.kafkademo.dto.ticket.CreateTicketResponse
import com.hegemonies.kafkademo.dto.ticket.GetTicketsResponse
import com.hegemonies.kafkademo.service.queue.IQueueService
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class QueueController(
    private val queueService: IQueueService,
) {

    @GetMapping("/tickets/new")
    suspend fun createTicket(@RequestParam type: TicketType): CreateTicketResponse {
        logger.debug("Receive /tickets/new request, type: $type")

        val ticketNumber = queueService.createTicket(type).getOrHandle { error ->
            throw error
        }

        logger.debug("Success handle of /tickets/new request, type: $type")

        return CreateTicketResponse(ticketNumber)
    }

    @GetMapping("/tickets/active")
    suspend fun getActiveTickets(): GetTicketsResponse {
        logger.debug("Receive /tickets/active request")

        val tickets = queueService.getActiveTickets().getOrHandle { error ->
            throw error
        }

        logger.debug("Success handle of /tickets/active request")

        return GetTicketsResponse(tickets)
    }

    @GetMapping("/tickets/get")
    suspend fun getTicket(
        @RequestParam(name = "ticket_number") ticketNumber: Long,
        @RequestHeader(HttpHeaderNames.AUTH_TOKEN) authToken: String
    ) {
        logger.debug("Receive /tickets/get request: ticketNumber=$ticketNumber, authToken=$authToken")

        queueService.getTicket(ticketNumber, authToken).getOrHandle { error ->
            throw error
        }

        logger.debug("Success handle of /tickets/get request: ticketNumber=$ticketNumber, authToken=$authToken")
    }

    companion object {
        private val logger = LoggerFactory.getLogger(this::class.java.declaringClass)
    }
}
