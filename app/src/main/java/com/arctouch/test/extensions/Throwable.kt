package com.arctouch.test.extensions

import com.arctouch.test.R
import java.io.IOException

val Throwable.errorType: ErrorType
    get() = when {
        this is IOException -> ErrorType.CONNECTION
        else -> ErrorType.UNEXPECTED
    }

val Throwable.friendlyMessage: Int
    get() = when (errorType) {
        ErrorType.CONNECTION -> R.string.network_error
        ErrorType.UNEXPECTED -> R.string.unexpected_error
    }

enum class ErrorType {
    CONNECTION, UNEXPECTED
}