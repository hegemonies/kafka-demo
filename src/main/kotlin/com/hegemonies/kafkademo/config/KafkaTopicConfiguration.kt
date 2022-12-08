package com.hegemonies.kafkademo.config

import com.hegemonies.kafkademo.consts.KafkaTopics
import org.apache.kafka.clients.admin.AdminClientConfig
import org.apache.kafka.clients.admin.NewTopic
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.core.KafkaAdmin

@Configuration
class KafkaTopicConfiguration(
    @Value(value = "\${spring.kafka.bootstrap-servers}")
    val bootstrapAddress: String,
) {

    @Bean
    fun kafkaAdmin(): KafkaAdmin {
        val configs = mapOf(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG to bootstrapAddress)
        return KafkaAdmin(configs)
    }

    @Bean
    fun topic1(): NewTopic {
        return NewTopic(KafkaTopics.TEST_TOPIC, 1, 1.toShort())
    }
}
