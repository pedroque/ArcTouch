package com.arctouch.test.data.repository

import com.arctouch.test.data.model.Config
import io.reactivex.Observable


interface ConfigRepository {
    fun getConfig() : Observable<Config>
}