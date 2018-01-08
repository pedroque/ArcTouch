package com.arctouch.test.data.repository.datasource

import com.arctouch.test.data.model.Genre
import io.reactivex.Observable


interface GenreDataSource {
    fun getGenres(): Observable<List<Genre>>
}