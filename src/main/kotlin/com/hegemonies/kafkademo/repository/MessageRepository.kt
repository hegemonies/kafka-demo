package com.hegemonies.kafkademo.repository

import com.hegemonies.kafkademo.model.Message
import org.springframework.data.r2dbc.repository.R2dbcRepository
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface MessageRepository : R2dbcRepository<Message, Long> {
//    fun findAllByMessage(message: String): List<Message>
}
