package com.arctouch.test.data.repository

import com.arctouch.test.data.model.Movies
import io.reactivex.Observable


interface MoviesRepository {
    fun getMovies(page: Int): Observable<Movies>
}