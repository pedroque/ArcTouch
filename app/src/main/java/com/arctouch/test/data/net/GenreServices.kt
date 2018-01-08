package com.arctouch.test.data.net

import com.arctouch.test.data.entity.GenresEntity
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query


interface GenreServices {
    @GET("genre/movie/list")
    fun getGenres(@Query("api_key") apiKey: String): Observable<GenresEntity>
}