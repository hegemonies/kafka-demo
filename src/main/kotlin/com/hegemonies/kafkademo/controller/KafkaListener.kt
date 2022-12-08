package com.hegemonies.kafkademo.controller

import com.hegemonies.kafkademo.consts.KafkaTopics
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class KafkaListener {

    @KafkaListener(topics = [KafkaTopics.TEST_TOPIC], groupId = "1")
    fun listenMessages(message: String) {
        logger.info("Received message from message broker: $message")
    }

    companion object {
        private val logger = LoggerFactory.getLogger(this.javaClass)
    }
}
