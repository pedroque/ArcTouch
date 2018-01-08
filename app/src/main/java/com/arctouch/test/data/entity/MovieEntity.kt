package com.arctouch.test.data.entity

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.util.*


class MovieEntity {
    @Expose
    var title: String? = null
    @Expose
    @SerializedName("poster_path")
    var posterPath: String? = null
    @Expose
    @SerializedName("backdrop_path")
    var backdropPath: String? = null
    @Expose
    @SerializedName("release_date")
    var releaseDate: Date? = null
    @Expose
    var overview: String? = null
    @Expose
    @SerializedName("genre_ids")
    var genreIds: List<Long>? = null
}