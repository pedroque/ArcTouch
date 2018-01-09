package com.arctouch.test.data.net

import com.arctouch.test.data.entity.MoviesEntity
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query


interface MovieServices {
    @GET("movie/upcoming")
    fun getUpcomingMovies(@Query("api_key") apiKey: String, @Query("page") page: Int): Observable<MoviesEntity>

    @GET("search/movie")
    fun searchMovie(@Query("api_key") apiKey: String, @Query("page") page: Int,
                    @Query("query") query: String): Observable<MoviesEntity>
}