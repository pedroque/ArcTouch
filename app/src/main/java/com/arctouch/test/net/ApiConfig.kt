package com.arctouch.test.net

interface ApiConfig {

    val baseUrl: String

    fun log(): Boolean
}
