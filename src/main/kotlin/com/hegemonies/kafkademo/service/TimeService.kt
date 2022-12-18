package com.hegemonies.kafkademo.service

import org.springframework.stereotype.Service
import java.time.Instant
import java.time.temporal.ChronoUnit

@Service
class TimeService {

    fun getTodayMidnightTimestamp(): Long = Instant.now().truncatedTo(ChronoUnit.DAYS).epochSecond
}
