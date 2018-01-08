package com.arctouch.test.data.cache

import com.arctouch.test.data.dao.GenreDao
import com.arctouch.test.data.model.Genre
import javax.inject.Inject


class GenreCache @Inject constructor() : Cache<List<Genre>> {
    private val dao = GenreDao()

    override fun save(t: List<Genre>) {
        dao.save(t)
    }

    override fun delete() {
        dao.delete()
    }

    override fun isExpired(): Boolean {
        val result = get()
        return result == null || result.isEmpty()
    }

    override fun get(): List<Genre>? {
        return dao.findAll()
    }
}