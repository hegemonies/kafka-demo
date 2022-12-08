package com.hegemonies.kafkademo.service

import com.hegemonies.kafkademo.dto.Message

interface IMessageService {
    suspend fun send(message: Message)
}
