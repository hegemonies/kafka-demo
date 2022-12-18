package com.hegemonies.kafkademo.config.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import java.time.Duration

@ConfigurationProperties(prefix = "token")
class TokenProperties {

    val lifetime: Duration = Duration.ofMinutes(10)
}
