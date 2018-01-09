@file:Suppress("UNCHECKED_CAST")

package com.arctouch.test.vm

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.arctouch.test.data.repository.ConfigRepository
import com.arctouch.test.data.repository.GenreRepository
import com.arctouch.test.data.repository.MoviesRepository
import com.arctouch.test.schedulers.ISchedulerProvider
import javax.inject.Inject

class MoviesViewModelFactory @Inject constructor(
        private val moviesRepository: MoviesRepository,
        private val schedulerProvider: ISchedulerProvider
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MoviesViewModel::class.java)) {
            return MoviesViewModel(moviesRepository, schedulerProvider) as T
        }
        if (modelClass.isAssignableFrom(SearchMoviesViewModel::class.java)) {
            return SearchMoviesViewModel(moviesRepository, schedulerProvider) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}