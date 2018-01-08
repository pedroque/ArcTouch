package com.arctouch.test.data.entity.mapper

import com.arctouch.test.data.entity.GenreEntity
import com.arctouch.test.data.model.Genre
import javax.inject.Inject


class GenreMapper @Inject constructor() : Mapper<Genre, GenreEntity> {
    override fun transform(entity: GenreEntity): Genre {
        val genre = Genre()
        genre.id = entity.id
        genre.name = entity.name
        return genre
    }
}