package com.arctouch.test.data.repository.datasource

import com.arctouch.test.data.dao.ConfigDao
import com.arctouch.test.data.model.Config
import io.reactivex.Observable
import javax.inject.Inject

class CacheConfigDataSource @Inject constructor() : ConfigDataSource {
    override fun getConfig(): Observable<Config> {
        return ConfigDao().find()?.let { Observable.just(it) } ?: Observable.empty()
    }
}