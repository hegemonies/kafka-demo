package com.hegemonies.kafkademo.repository

import com.hegemonies.kafkademo.model.Message
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface MessageRepository : CrudRepository<Message, Long> {
    fun findAllByMessage(message: String): List<Message>
}
