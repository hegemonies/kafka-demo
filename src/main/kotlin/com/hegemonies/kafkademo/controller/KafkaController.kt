package com.hegemonies.kafkademo.controller

import com.hegemonies.kafkademo.dto.Message
import com.hegemonies.kafkademo.service.IMessageService
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class KafkaController(
    private val messageService: IMessageService,
) {

    @PostMapping("send")
    suspend fun sendMessage(@RequestBody message: Message) {
        logger.info("Received message [$message], send it to message broker")
        messageService.send(message)
    }

    companion object {
        private val logger = LoggerFactory.getLogger(this::class.java.declaringClass)
    }
}
