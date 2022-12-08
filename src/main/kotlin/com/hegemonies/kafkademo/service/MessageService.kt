package com.hegemonies.kafkademo.service

import com.hegemonies.kafkademo.consts.KafkaTopics
import com.hegemonies.kafkademo.dto.Message
import org.slf4j.LoggerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service

@Service
class MessageService(
    private val kafkaTemplate: KafkaTemplate<String, String>,
) : IMessageService {

    override suspend fun send(message: Message) {
        kafkaTemplate.send(KafkaTopics.TEST_TOPIC, message.message).thenAccept {
            logger.info("Success send message to message broker")
        }.exceptionally { error ->
            logger.error("Failed to send message to message broker: ", error)
            throw error
        }
//        kafkaTemplate.send(KafkaTopics.TEST_TOPIC, message.message).get()
    }

    companion object {
        private val logger = LoggerFactory.getLogger(this::class.java.declaringClass)
    }
}
