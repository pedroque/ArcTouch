package com.arctouch.test.data.entity.mapper

import com.arctouch.test.data.entity.MovieEntity
import com.arctouch.test.data.model.Movie
import javax.inject.Inject


class MovieMapper @Inject constructor() : Mapper<Movie, MovieEntity> {
    override fun transform(entity: MovieEntity): Movie {
        val movie = Movie()
        movie.title = entity.title
        movie.backdropPath = entity.backdropPath
        movie.posterPath = entity.posterPath
        movie.overview = entity.overview
        movie.releaseDate = entity.releaseDate
        movie.genreIds = mutableListOf()
        entity.genreIds?.mapTo(movie.genreIds, { it })
        return movie
    }
}