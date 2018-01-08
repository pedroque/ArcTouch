package com.arctouch.test.data.repository

import com.arctouch.test.data.cache.GenreCache
import com.arctouch.test.data.model.Genre
import com.arctouch.test.data.repository.datasource.GenreDataSource
import com.arctouch.test.di.Named
import io.reactivex.Observable
import javax.inject.Inject


class GenreRepositoryImpl @Inject constructor(
        private val genreCache: GenreCache,
        @Named("cloud")
        private val cloudGenreDataSource: GenreDataSource,
        @Named("cache")
        private val cacheGenreDataSource: GenreDataSource
) : GenreRepository {
    override fun getGenres(): Observable<List<Genre>> {
        return if (genreCache.isExpired()) {
            cloudGenreDataSource.getGenres().map {
                genreCache.save(it)
                return@map it
            }
        } else {
            cacheGenreDataSource.getGenres()
        }
    }
}