package com.hegemonies.kafkademo.listener

import com.hegemonies.kafkademo.consts.KafkaTopics
import com.hegemonies.kafkademo.model.Message
import com.hegemonies.kafkademo.repository.MessageRepository
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class KafkaListener(
    private val messageRepository: MessageRepository,
) {

    @KafkaListener(topics = [KafkaTopics.TEST_TOPIC], groupId = "1")
    fun listenMessages(message: String) {
        logger.info("Received message from message broker: $message")
        messageRepository.save(Message(message = message)).block()
    }

    companion object {
        private val logger = LoggerFactory.getLogger(this::class.java.declaringClass)
    }
}
