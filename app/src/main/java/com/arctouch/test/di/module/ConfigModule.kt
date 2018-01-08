package com.arctouch.test.di.module

import com.arctouch.test.data.cache.Cache
import com.arctouch.test.data.cache.ConfigCache
import com.arctouch.test.data.entity.ConfigEntity
import com.arctouch.test.data.entity.mapper.ConfigMapper
import com.arctouch.test.data.entity.mapper.Mapper
import com.arctouch.test.data.model.Config
import com.arctouch.test.data.repository.ConfigRepository
import com.arctouch.test.data.repository.ConfigRepositoryImpl
import com.arctouch.test.di.ActivityScope
import com.arctouch.test.schedulers.ISchedulerProvider
import com.arctouch.test.vm.ConfigViewModelFactory
import dagger.Module
import dagger.Provides
import dagger.Reusable

@Module
class ConfigModule {
    @Provides
    @Reusable
    internal fun provideConfigCache(configCache: ConfigCache): Cache<Config> = configCache

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