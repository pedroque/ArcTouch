package com.arctouch.test.data.cache

import com.arctouch.test.dao.ConfigDao
import com.arctouch.test.data.model.Config
import com.arctouch.test.utils.DateHelper
import javax.inject.Inject


class ConfigCache @Inject constructor(): Cache<Config> {

    private val dao = ConfigDao()

    override fun delete() {
        get()?.let { dao.delete(it) }
    }

    override fun isExpired() = get()?.let { it.date.time < System.currentTimeMillis() - DateHelper.DAY * 3 } ?: true

    override fun save(t: Config) {
        dao.save(t)
    }

    override fun get() = dao.find()
}