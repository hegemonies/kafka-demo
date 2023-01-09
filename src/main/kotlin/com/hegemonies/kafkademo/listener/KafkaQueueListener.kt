package com.hegemonies.kafkademo.listener

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.hegemonies.kafkademo.consts.KafkaConst
import com.hegemonies.kafkademo.consts.KafkaTopics
import com.hegemonies.kafkademo.model.Ticket
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.stereotype.Component
import kotlin.time.Duration.Companion.seconds

@Component
class KafkaQueueListener(
    private val jsonMapper: ObjectMapper
) {

    @KafkaListener(topics = [KafkaTopics.ELECTRONIC_QUEUE_TOPIC], groupId = KafkaConst.ELECTRONIC_QUEUE_GROUP_ID)
    fun handle(@Payload message: String) {
        val ticket = message.toTicket()
        logger.info("Received ticket #${ticket.ticketNumber}")
    }

    private fun String.toTicket(): Ticket =
        jsonMapper.readValue(this)

    companion object {
        private val logger = LoggerFactory.getLogger(this::class.java.declaringClass)
    }
}
