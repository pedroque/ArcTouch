package com.arctouch.test.data.net

import com.arctouch.test.data.entity.ConfigResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface ConfigServices {
    @GET("configuration")
    fun getConfig(@Query("api_key") apiKey: String) : Observable<ConfigResponse>
}