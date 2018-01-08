package com.arctouch.test.data.model


class Movies {
    var page: Int = 0
    var totalPages: Int = 0
    lateinit var movies: MutableList<Movie>
}