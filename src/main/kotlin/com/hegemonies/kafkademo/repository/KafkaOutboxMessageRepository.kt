package com.hegemonies.kafkademo.repository

import com.hegemonies.kafkademo.model.KafkaOutboxMessage
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface KafkaOutboxMessageRepository : CoroutineCrudRepository<KafkaOutboxMessage, Long>
