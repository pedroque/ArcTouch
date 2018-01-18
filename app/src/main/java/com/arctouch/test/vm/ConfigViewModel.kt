package com.arctouch.test.vm

import android.arch.lifecycle.MutableLiveData
import com.arctouch.test.data.model.Config
import com.arctouch.test.data.repository.ConfigRepository
import com.arctouch.test.schedulers.ISchedulerProvider
import io.reactivex.Observable

class ConfigViewModel constructor(
        private val configRepository: ConfigRepository,
        schedulerProvider: ISchedulerProvider
) : BaseViewModel(schedulerProvider) {

    val config = MutableLiveData<Config>()

    fun fetchConfig(): Observable<Config> {
        return execute(configRepository.getConfig(), {
            config.postValue(it)
        }, {

        })
    }
}