package com.arctouch.test.data.entity

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class ConfigEntity {
    @Expose
    @SerializedName("base_url")
    var baseUrl : String? = null

    @Expose
    @SerializedName("secure_base_url")
    var secureBaseUrl : String? = null

    @Expose
    @SerializedName("backdrop_sizes")
    var backdropSizes : List<String>? = null

    @Expose
    @SerializedName("poster_sizes")
    var posterSizes : List<String>? = null
}