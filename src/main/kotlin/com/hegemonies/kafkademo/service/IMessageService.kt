package com.hegemonies.kafkademo.service

import com.hegemonies.kafkademo.dto.MessageDto

interface IMessageService {
    suspend fun send(message: MessageDto)
}
