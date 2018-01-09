package com.arctouch.test.data.repository

import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.arctouch.test.data.cache.Cache
import com.arctouch.test.data.model.Config
import com.arctouch.test.data.model.Genre
import com.arctouch.test.data.repository.datasource.ConfigDataSource
import com.arctouch.test.data.repository.datasource.GenreDataSource
import com.arctouch.test.data.repository.datasource.MovieDataSource
import io.reactivex.Observable
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

class ConfigRepositoryImplTest {
    @Rule
    @JvmField
    val rule: TestRule = InstantTaskExecutorRule()

    @Mock
    lateinit var cloudConfigDataSource: ConfigDataSource
    @Mock
    lateinit var cacheConfigDataSource: ConfigDataSource
    @Mock
    lateinit var configCache: Cache<Config>

    lateinit var configRepository: ConfigRepository

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        configRepository = ConfigRepositoryImpl(configCache, cacheConfigDataSource, cloudConfigDataSource)
    }

    @Test
    fun getValidCache() {
        Mockito.`when`(configCache.isExpired()).thenReturn(false)
        val config = Config()
        Mockito.`when`(cacheConfigDataSource.getConfig()).thenReturn(Observable.just(config))
        configRepository.getConfig()
                .test()
                .assertNoErrors()
                .assertValue(config)
                .assertComplete()
                .awaitTerminalEvent()
        verify(cacheConfigDataSource).getConfig()
    }

    @Test
    fun getExpiredCache() {
        Mockito.`when`(configCache.isExpired()).thenReturn(true)
        val config = Config()
        Mockito.`when`(cloudConfigDataSource.getConfig()).thenReturn(Observable.just(config))
        configRepository.getConfig()
                .test()
                .assertNoErrors()
                .assertValue(config)
                .assertComplete()
                .awaitTerminalEvent()
        verify(cloudConfigDataSource).getConfig()
        verify(configCache).save(config)
    }

}