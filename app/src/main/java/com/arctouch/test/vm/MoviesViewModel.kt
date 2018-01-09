package com.arctouch.test.vm

import android.arch.lifecycle.MutableLiveData
import com.arctouch.test.data.model.Movies
import com.arctouch.test.data.repository.MoviesRepository
import com.arctouch.test.schedulers.ISchedulerProvider
import io.reactivex.Observable

class MoviesViewModel constructor(
        private val moviesRepository: MoviesRepository,
        schedulerProvider: ISchedulerProvider
) : BaseViewModel(schedulerProvider) {
    val movies = MutableLiveData<Resource<Movies>>()

    fun nextPage(): Observable<Movies> {
        val nextPage = movies.value?.data?.page?.plus(1) ?: 1
        movies.postValue(Resource.loading(movies.value?.data?.let { it }))
        return execute(moviesRepository.getMovies(nextPage), {
            movies.value?.data?.let { previous ->
                it.movies.addAll(0, previous.movies)
            }
            movies.postValue(Resource.success(it))
        }, {
            movies.postValue(Resource.error(it))
        })
    }
}