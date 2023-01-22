package com.hegemonies.kafkademo.service.queue

import arrow.core.Either
import arrow.core.getOrHandle
import com.fasterxml.jackson.databind.ObjectMapper
import com.hegemonies.kafkademo.consts.KafkaTopics
import com.hegemonies.kafkademo.dto.ErrorResult
import com.hegemonies.kafkademo.dto.TicketDto
import com.hegemonies.kafkademo.dto.TicketType
import com.hegemonies.kafkademo.mapper.toDto
import com.hegemonies.kafkademo.mapper.toEither
import com.hegemonies.kafkademo.mapper.toErrorResult
import com.hegemonies.kafkademo.model.KafkaOutboxMessage
import com.hegemonies.kafkademo.model.Ticket
import com.hegemonies.kafkademo.repository.KafkaOutboxMessageRepository
import com.hegemonies.kafkademo.repository.TicketRepository
import com.hegemonies.kafkademo.service.TimeService
import com.hegemonies.kafkademo.service.auth.AuthService
import kotlinx.coroutines.reactive.awaitFirstOrNull
import org.slf4j.LoggerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import java.time.Instant
import java.util.UUID

@Service
class QueueService(
    private val ticketRepository: TicketRepository,
    private val timeService: TimeService,
//    private val kafkaTemplate: KafkaTemplate<String, Ticket>,
//    private val kafkaConsumer: KafkaConsumer<String, Ticket>,
    private val kafkaOutboxMessageRepository: KafkaOutboxMessageRepository,
    private val authService: AuthService,
    private val jsonObjectMapper: ObjectMapper,
) : IQueueService {

    override suspend fun getActiveTickets(): Either<ErrorResult, Flux<TicketDto>> {
        val todayMidnight = timeService.getTodayMidnightTimestamp()

        val tickets = Either.catch {
            ticketRepository.findAllByCreatedAtAfterAndClosedAtNotNull(todayMidnight).map { ticket ->
                ticket.toDto()
            }
        }.getOrHandle { error ->
            return error.toErrorResult().toEither()
        }

        return Either.Right(tickets)
    }

    override suspend fun createTicket(ticketType: TicketType): Either<ErrorResult, Long> {
        logger.debug("Receive message from kafka for create ticket with type: $ticketType")

        val todayMidnight = timeService.getTodayMidnightTimestamp()
        val offset = ticketType.getOffset()

        val lastTicket = Either.catch {
            ticketRepository.findTopByCreatedAtAfterAndType(todayMidnight, ticketType.shortValue)
                .awaitFirstOrNull()
        }.getOrHandle { error ->
            return error.toErrorResult().toEither()
        }

        val ticket = Ticket(
            ticketNumber = (lastTicket?.ticketNumber ?: offset) + 1,
            type = ticketType.shortValue,
            createdAt = System.currentTimeMillis(),
        )

        Either.catch {
            ticketRepository.save(ticket).awaitFirstOrNull()?.also { ticket ->
                logger.debug("Successful created ticket #${ticket.id}")
            }
        }.getOrHandle { error ->
            return error.toErrorResult().toEither()
        }

        Either.catch {
//            kafkaTemplate.send(KafkaTopics.ELECTRONIC_QUEUE_TOPIC, ticket)
            kafkaOutboxMessageRepository.save(
                KafkaOutboxMessage(
                    timestamp = Instant.now(),
                    topic = KafkaTopics.ELECTRONIC_QUEUE_TOPIC,
                    key = ticket.id?.toString() ?: UUID.randomUUID().toString(),
                    message = jsonObjectMapper.writeValueAsString(ticket)
                )
            )
        }.getOrHandle { error ->
            return error.toErrorResult().toEither()
        }

        return Either.Right(ticket.ticketNumber)
    }

    override suspend fun getTicket(ticketNumber: Long, authToken: String): Either<ErrorResult, Long> {
        val isValidToken = authService.checkToken(authToken).getOrHandle { error ->
            return error.toErrorResult().toEither()
        }

        if (!isValidToken) {
            return ErrorResult(
                message = "Auth token is invalid, please re-auth",
                code = 400
            ).toEither()
        }

        Either.catch {
            // not working
            // val records = kafkaConsumer.poll(Duration.ofSeconds(2))
            // for (record in records) {
            //     val ticket = record.value()
            //     logger.debug("Get ticket: $ticket")
            //     kafkaConsumer.commitAsync()
            //     return Either.Right(ticket.ticketNumber)
            // }
        }.getOrHandle { error ->
            return error.toErrorResult().toEither()
        }

        return Either.Left(
            ErrorResult(
                message = "Did not found ticket",
                code = 400
            )
        )
    }

    companion object {
        private val logger = LoggerFactory.getLogger(this::class.java.declaringClass)
    }
}
