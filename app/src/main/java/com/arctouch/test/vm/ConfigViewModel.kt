package com.arctouch.test.vm

import com.arctouch.test.data.model.Config
import com.arctouch.test.data.repository.ConfigRepository
import com.arctouch.test.schedulers.ISchedulerProvider
import io.reactivex.Observable

class ConfigViewModel constructor(
        private val configRepository: ConfigRepository,
        schedulerProvider: ISchedulerProvider
) : BaseViewModel(schedulerProvider) {

    fun getConfig(): Observable<Config> {
        return configRepository.getConfig()
                .subscribeOn(schedulerProvider.computation())
                .observeOn(schedulerProvider.ui())
    }
}