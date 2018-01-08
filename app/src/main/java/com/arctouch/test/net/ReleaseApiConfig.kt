package com.arctouch.test.net

import javax.inject.Inject

class ReleaseApiConfig @Inject
constructor() : ApiConfig {

    override val baseUrl: String
        get() = "https://api.themoviedb.org/3/"

    override fun log(): Boolean {
        return false
    }
}
