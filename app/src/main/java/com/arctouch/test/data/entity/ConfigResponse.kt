package com.arctouch.test.data.entity

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class ConfigResponse {
    @Expose
    @SerializedName("images")
    lateinit var images: ConfigEntity
}