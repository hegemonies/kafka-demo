package com.hegemonies.kafkademo.config

import com.hegemonies.kafkademo.consts.KafkaTopics
import com.hegemonies.kafkademo.model.Ticket
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.clients.consumer.KafkaConsumer
import org.apache.kafka.common.serialization.StringDeserializer
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.support.serializer.JsonDeserializer

@Configuration
class KafkaConsumerConfiguration(
    @Value(value = "\${spring.kafka.bootstrap-servers}")
    val bootstrapAddress: String,
) {

    @Bean
    fun kafkaConsumer(): KafkaConsumer<String, Ticket> {
        val props = mapOf(
            ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG to bootstrapAddress,
            ConsumerConfig.GROUP_ID_CONFIG to "electronic_queue_group",
            ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG to StringDeserializer::class.java,
            ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG to JsonDeserializer::class.java
        )

        val consumer = KafkaConsumer<String, Ticket>(props)
        consumer.subscribe(listOf(KafkaTopics.ELECTRONIC_QUEUE_TOPIC))

        return consumer
    }
}
