package com.hegemonies.kafkademo.dto.auth

data class AuthRequest(
    val username: String,
    val password: String,
)
