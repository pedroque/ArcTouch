package com.arctouch.test.data.repository

import com.arctouch.test.data.model.Movies
import com.arctouch.test.data.repository.datasource.MovieDataSource
import com.arctouch.test.di.Named
import io.reactivex.Observable
import javax.inject.Inject


class MoviesRepositoryImpl @Inject constructor(
        @Named("cloud")
        private val cloudMoviesDataSource: MovieDataSource
) : MoviesRepository {
    override fun getMovies(page: Int): Observable<Movies> {
        return cloudMoviesDataSource.getUpcomingMovies(page)
    }
}