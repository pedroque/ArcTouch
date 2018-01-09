package com.arctouch.test.vm

import com.arctouch.test.R
import com.arctouch.test.extensions.friendlyMessage

data class Resource<out T>(
        val status: Status,
        val data: T? = null,
        private val error: Throwable? = null
) {
    val message: Int
        get() = error?.friendlyMessage ?: R.string.unexpected_error

    val isEmpty: Boolean
        get() = data == null || (data is Collection<*> && data.isEmpty())

    companion object {
        fun <T> loading(data: T?) = Resource(Status.LOADING, data)
        fun <T> success(data: T?) = Resource(Status.SUCCESS, data)
        fun <T> error(error: Throwable, data: T? = null) = Resource(Status.ERROR, data, error)
    }

    enum class Status {
        SUCCESS, ERROR, LOADING
    }
}