package com.arctouch.test.data.model

import java.util.*


class Movie {
    var title: String? = null
    var posterPath: String? = null
    var backdropPath: String? = null
    var releaseDate: Date? = null
    var overview: String? = null
    lateinit var genreIds: MutableList<Long>
}