package com.arctouch.test.data.repository

import com.arctouch.test.data.model.Genre
import io.reactivex.Observable


interface GenreRepository {
    fun getGenres(): Observable<List<Genre>>
}