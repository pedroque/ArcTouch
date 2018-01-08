package com.arctouch.test.data.repository.datasource

import com.arctouch.test.data.entity.mapper.GenreMapper
import com.arctouch.test.data.model.Genre
import com.arctouch.test.data.net.GenreServices
import com.arctouch.test.di.Named
import io.reactivex.Observable
import javax.inject.Inject

class CloudGenreDataSource @Inject constructor(
        @Named("api_key")
        private val apiKey: String,
        private val services: GenreServices,
        private val mapper: GenreMapper) : GenreDataSource {

    override fun getGenres(): Observable<List<Genre>> {
        return services.getGenres(apiKey).map { it.genres.map { mapper.transform(it) } }
    }
}