package com.hegemonies.kafkademo.dto

data class ErrorResult(
    override val message: String,
    val code: Int
) : Exception()
