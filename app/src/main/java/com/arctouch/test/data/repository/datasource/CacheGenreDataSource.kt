package com.arctouch.test.data.repository.datasource

import com.arctouch.test.data.dao.GenreDao
import com.arctouch.test.data.model.Genre
import io.reactivex.Observable
import javax.inject.Inject


class CacheGenreDataSource @Inject constructor() : GenreDataSource {
    override fun getGenres(): Observable<List<Genre>> {
        return GenreDao().findAll()?.let { Observable.just(it) } ?: Observable.empty()
    }
}