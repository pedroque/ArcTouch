package com.arctouch.test.di.module

import com.arctouch.test.data.cache.Cache
import com.arctouch.test.data.cache.ConfigCache
import com.arctouch.test.data.entity.ConfigEntity
import com.arctouch.test.data.entity.mapper.ConfigMapper
import com.arctouch.test.data.entity.mapper.Mapper
import com.arctouch.test.data.model.Config
import com.arctouch.test.data.repository.ConfigRepository
import com.arctouch.test.data.repository.ConfigRepositoryImpl
import com.arctouch.test.data.repository.datasource.CacheConfigDataSource
import com.arctouch.test.data.repository.datasource.CloudConfigDataSource
import com.arctouch.test.data.repository.datasource.ConfigDataSource
import com.arctouch.test.di.ActivityScope
import com.arctouch.test.di.Named
import com.arctouch.test.schedulers.ISchedulerProvider
import com.arctouch.test.vm.ConfigViewModelFactory
import dagger.Module
import dagger.Provides
import dagger.Reusable

@Module
class ConfigModule {
    @Provides
    @Reusable
    @Named("config")
    internal fun provideConfigCache(configCache: ConfigCache): Cache<Config> = configCache

    @Provides
    @Reusable
    @Named("cloud")
    internal fun provideCloudConfigDataSource(cloudConfigDataSource: CloudConfigDataSource): ConfigDataSource = cloudConfigDataSource

    @Provides
    @Reusable
    @Named("cache")
    internal fun provideCacheConfigDataSource(cacheConfigDataSource: CacheConfigDataSource): ConfigDataSource = cacheConfigDataSource

    @Provides
    @Reusable
    internal fun provideConfigMapper(configMapper: ConfigMapper): Mapper<Config, ConfigEntity> = configMapper

    @Provides
    @Reusable
    internal fun provideConfigRepository(configRepository: ConfigRepositoryImpl): ConfigRepository = configRepository

    @Provides
    @ActivityScope
    internal fun provideConfigViewModelFactory(configRepository: ConfigRepository, schedulerProvider: ISchedulerProvider) =
            ConfigViewModelFactory(configRepository, schedulerProvider)
}