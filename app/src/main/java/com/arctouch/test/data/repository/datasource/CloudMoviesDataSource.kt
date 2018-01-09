package com.arctouch.test.data.repository.datasource

import com.arctouch.test.data.entity.mapper.MoviesMapper
import com.arctouch.test.data.model.Movies
import com.arctouch.test.data.net.MovieServices
import com.arctouch.test.di.Named
import io.reactivex.Observable
import java.util.concurrent.TimeUnit
import javax.inject.Inject


class CloudMoviesDataSource @Inject constructor(
        @Named("api_key")
        private val apiKey: String,
        private val services: MovieServices,
        private val mapper: MoviesMapper) : MovieDataSource {
    override fun getUpcomingMovies(page: Int): Observable<Movies> {
        return services.getUpcomingMovies(apiKey, page).map { mapper.transform(it) }
    }

    override fun searchMovie(page: Int, query: String): Observable<Movies> {
        return services.searchMovie(apiKey, page, query).map { mapper.transform(it) }
    }
}