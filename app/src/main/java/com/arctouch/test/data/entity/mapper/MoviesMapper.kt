package com.arctouch.test.data.entity.mapper

import com.arctouch.test.data.entity.MoviesEntity
import com.arctouch.test.data.model.Movies
import javax.inject.Inject

class MoviesMapper @Inject constructor(private val movieMapper: MovieMapper) : Mapper<Movies, MoviesEntity> {

    override fun transform(entity: MoviesEntity): Movies {
        val movies = Movies()
        movies.page = entity.page
        movies.totalPages = entity.totalPages
        movies.movies = mutableListOf()
        entity.movies?.mapTo(movies.movies, { movieMapper.transform(it) })
        return movies
    }

}