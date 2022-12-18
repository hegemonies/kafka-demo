package com.hegemonies.kafkademo.mapper

import arrow.core.Either
import com.hegemonies.kafkademo.dto.ErrorResult

fun Throwable.toErrorResult(): ErrorResult =
    ErrorResult(
        message = this.message ?: "Unknown error message",
        code = 500
    )

fun ErrorResult.toEither(): Either.Left<ErrorResult> =
    Either.Left(this)
