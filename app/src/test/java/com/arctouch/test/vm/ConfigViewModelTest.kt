package com.arctouch.test.vm

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.lifecycle.Observer
import com.arctouch.test.data.model.Config
import com.arctouch.test.data.repository.ConfigRepository
import com.arctouch.test.schedulers.TestScheduleProvider
import io.reactivex.Observable
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

class ConfigViewModelTest {
    @Rule
    @JvmField
    val rule: TestRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var configRepository: ConfigRepository
    @Mock
    private lateinit var observer: Observer<Config>

    private lateinit var configViewModel: ConfigViewModel

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        configViewModel = ConfigViewModel(configRepository, TestScheduleProvider())
    }

    @Test
    fun getConfig() {
        val config = Config()
        Mockito.`when`(configRepository.getConfig()).thenReturn(Observable.just(config))
        configViewModel.config.observeForever(observer)
        configViewModel.fetchConfig()
                .test()
                .assertNoErrors()
                .assertComplete()
                .assertValue(config)
                .awaitTerminalEvent()
        verify(observer).onChanged(config)
    }

}