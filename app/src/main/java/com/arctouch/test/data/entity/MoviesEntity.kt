package com.arctouch.test.data.entity

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class MoviesEntity {
    @Expose
    @SerializedName("results")
    var movies: List<MovieEntity>? = null

    @Expose
    var page: Int = 0

    @Expose
    @SerializedName("total_pages")
    var totalPages: Int = 0
}