@file:Suppress("UNCHECKED_CAST")

package com.arctouch.test.vm

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.arctouch.test.data.repository.ConfigRepository
import com.arctouch.test.schedulers.ISchedulerProvider
import javax.inject.Inject

class ConfigViewModelFactory @Inject constructor(
        private val configRepository: ConfigRepository,
        private val schedulerProvider: ISchedulerProvider
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ConfigViewModel::class.java)) {
            return ConfigViewModel(configRepository, schedulerProvider) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}