package com.arctouch.test.data.repository

import com.arctouch.test.data.cache.Cache
import com.arctouch.test.data.model.Config
import com.arctouch.test.data.repository.datasource.ConfigDataSource
import com.arctouch.test.di.Named
import io.reactivex.Observable
import javax.inject.Inject

class ConfigRepositoryImpl @Inject constructor(
        @Named("config")
        private val configCache: Cache<Config>,
        @Named("cache")
        private val cacheConfigDataStore: ConfigDataSource,
        @Named("cloud")
        private val cloudConfigDataStore: ConfigDataSource
) : ConfigRepository {
    override fun getConfig(): Observable<Config> {
        return if (configCache.isExpired()) {
            cloudConfigDataStore.getConfig().map {
                configCache.save(it)
                return@map it
            }
        } else {
            cacheConfigDataStore.getConfig()
        }
    }
}