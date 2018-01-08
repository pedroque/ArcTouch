package com.arctouch.test.data.repository

import com.arctouch.test.data.cache.ConfigCache
import com.arctouch.test.data.model.Config
import com.arctouch.test.data.repository.datasource.CacheConfigDataSource
import com.arctouch.test.data.repository.datasource.CloudConfigDataSource
import io.reactivex.Observable
import javax.inject.Inject

class ConfigRepositoryImpl @Inject constructor(
        private val configCache: ConfigCache,
        private val cacheConfigDataStore: CacheConfigDataSource,
        private val cloudConfigDataStore: CloudConfigDataSource
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