package com.arctouch.test.vm

import com.arctouch.test.data.model.Movies

class MoviesResource(
        status: Status,
        data: Movies? = null,
        error: Throwable? = null
) : Resource<Movies>(status, data, error) {
    override val isEmpty: Boolean
        get() = data == null || (data.movies.isEmpty())

    companion object {
        fun loading(data: Movies?) = MoviesResource(Status.LOADING, data)
        fun success(data: Movies?) = MoviesResource(Status.SUCCESS, data)
        fun error(error: Throwable, data: Movies? = null) = MoviesResource(Status.ERROR, data, error)
    }
}