package com.arctouch.test.data.repository.datasource

import com.arctouch.test.data.model.Config
import io.reactivex.Observable


interface ConfigDataSource {
    fun getConfig() : Observable<Config>
}