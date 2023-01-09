package com.hegemonies.kafkademo.config

import com.hegemonies.kafkademo.consts.KafkaConst
import com.hegemonies.kafkademo.consts.KafkaTopics
import com.hegemonies.kafkademo.dto.AbstractDto
import com.hegemonies.kafkademo.model.Ticket
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.clients.consumer.KafkaConsumer
import org.apache.kafka.common.serialization.StringDeserializer
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.config.KafkaListenerConfigUtils
import org.springframework.kafka.config.KafkaListenerContainerFactory
import org.springframework.kafka.core.ConsumerFactory
import org.springframework.kafka.core.DefaultKafkaConsumerFactory
import org.springframework.kafka.retrytopic.ListenerContainerFactoryConfigurer
import org.springframework.kafka.support.converter.StringJsonMessageConverter
import org.springframework.kafka.support.serializer.JsonDeserializer
import java.io.Serializable


@Configuration
class KafkaConsumerConfiguration(
    @Value(value = "\${spring.kafka.bootstrap-servers}")
    val bootstrapAddress: String,
) {

    private val kafkaConsumerProps: Map<String, Serializable> =
        mapOf(
            ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG to bootstrapAddress,
            ConsumerConfig.GROUP_ID_CONFIG to KafkaConst.ELECTRONIC_QUEUE_GROUP_ID,
            ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG to StringDeserializer::class.java,
            ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG to StringDeserializer::class.java,
            ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG to true,
            ConsumerConfig.MAX_POLL_RECORDS_CONFIG to 10,
        )

    @Bean
    @Primary
    fun kafkaConsumerFactory(): ConsumerFactory<String, String> {
        return DefaultKafkaConsumerFactory(kafkaConsumerProps)
    }

//    @Bean
//    @Primary
//    fun kafkaListenerContainerFactory(
//        kafkaConsumerFactory: ConsumerFactory<String, String>
//    ): ConcurrentKafkaListenerContainerFactory<String, String> {
//        val factory = ConcurrentKafkaListenerContainerFactory<String, String>().apply {
//            consumerFactory = kafkaConsumerFactory
//            this.setThreadNameSupplier {
//                "foo-${it.listenerId}"
//            }
//            this.setChangeConsumerThreadName(true)
//        }
//        return factory
//    }

//    @Bean
//    fun kafkaConsumer(): KafkaConsumer<String, Ticket> {
//        val consumer = KafkaConsumer<String, Ticket>(kafkaConsumerProps)
//        consumer.subscribe(listOf(KafkaTopics.ELECTRONIC_QUEUE_TOPIC))
//
//        return consumer
//    }
//
//    @Bean
//    fun singleFactory(kafkaConsumerFactory: ConsumerFactory<String, AbstractDto>): KafkaListenerContainerFactory<*> {
//        val factory = ConcurrentKafkaListenerContainerFactory<String, AbstractDto>().apply {
//            consumerFactory = kafkaConsumerFactory
//            isBatchListener = false
//            setMessageConverter(StringJsonMessageConverter())
//        }
//        return factory
//    }
}
