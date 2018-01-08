package com.arctouch.test.data.net

interface ApiConfig {

    val baseUrl: String

    fun log(): Boolean
}
