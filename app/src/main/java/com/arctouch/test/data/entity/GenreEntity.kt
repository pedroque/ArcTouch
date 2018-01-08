package com.arctouch.test.data.entity

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class GenreEntity {
    @Expose
    @SerializedName("id")
    var id: Long = 0
    @Expose
    @SerializedName("name")
    var name: String? = null
}