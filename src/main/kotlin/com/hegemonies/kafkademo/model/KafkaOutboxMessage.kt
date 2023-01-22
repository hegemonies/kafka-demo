package com.hegemonies.kafkademo.model

import com.hegemonies.kafkademo.consts.TableNames
import jakarta.persistence.Id
import org.springframework.data.relational.core.mapping.Table
import java.time.Instant

@Table(name = TableNames.OUTBOX)
data class KafkaOutboxMessage(
    @Id
    val id: Long? = null,

    val timestamp: Instant,

    val topic: String,

    val key: String,

    val message: String,
)
