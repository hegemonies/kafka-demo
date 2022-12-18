package com.hegemonies.kafkademo.dto.registration

data class RegistrationRequest(
    val username: String,
    val password: String,
    val passwordAgain: String
)
