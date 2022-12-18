package com.hegemonies.kafkademo.config

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.SupervisorJob
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import kotlin.coroutines.CoroutineContext

@Configuration
class CoroutineScopeConfiguration {

    @Bean
    @OptIn(ExperimentalCoroutinesApi::class)
    fun kafkaCoroutineScope(): CoroutineScope =
        object : CoroutineScope {
            override val coroutineContext: CoroutineContext =
                Dispatchers.IO.limitedParallelism(2) + SupervisorJob()
        }
}
