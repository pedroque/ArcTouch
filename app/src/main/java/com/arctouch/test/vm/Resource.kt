package com.arctouch.test.vm

import android.databinding.BindingAdapter
import android.view.View
import com.arctouch.test.extensions.friendlyMessage
import com.arctouch.test.extensions.hide
import com.arctouch.test.extensions.show

data class Resource<out T>(
        val status: Status,
        val data: T? = null,
        private val error: Throwable? = null
) {
    val message: Int
        get() = error?.friendlyMessage ?: 0

    val isEmpty: Boolean
        get() = data == null || (data is Collection<*> && data.isEmpty())

    companion object {
        fun loading() = Resource(Status.LOADING, null)
        fun <T> success(data: T?) = Resource(Status.SUCCESS, data)
        fun <T> error(error: Throwable, data: T? = null) = Resource(Status.ERROR, data, error)

        @JvmStatic
        @BindingAdapter("app:showIf")
        fun showIf(view: View, show: Boolean) {
            if( show ) view.show() else view.hide()
        }
    }

    enum class Status {
        SUCCESS, ERROR, LOADING
    }
}