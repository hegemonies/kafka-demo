package com.hegemonies.kafkademo.controller

import com.hegemonies.kafkademo.consts.KafkaTopics
import com.hegemonies.kafkademo.dto.Message
import org.slf4j.LoggerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class KafkaController(
    private val kafkaTemplate: KafkaTemplate<String, String>,
) {

    @PostMapping("send")
    suspend fun sendMessage(message: Message) {
        logger.info("Received message [$message], send it to message broker")

        kafkaTemplate.send(KafkaTopics.TEST_TOPIC, message.message).addCallback(
            {
                logger.info("Success send message to message broker")
            },
            { error ->
                logger.error("Failed to send message to message broker: ", error)
            }
        )
    }

    companion object {
        private val logger = LoggerFactory.getLogger(this.javaClass)
    }
}
