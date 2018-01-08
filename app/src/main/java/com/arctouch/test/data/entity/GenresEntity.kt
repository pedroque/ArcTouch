package com.arctouch.test.data.entity

import com.google.gson.annotations.Expose


class GenresEntity {
    @Expose
    lateinit var genres: List<GenreEntity>
}