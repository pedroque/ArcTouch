package com.arctouch.test.data.repository.datasource

import com.arctouch.test.data.model.Movies
import io.reactivex.Observable


interface MovieDataSource {
    fun getUpcomingMovies(page: Int): Observable<Movies>
}