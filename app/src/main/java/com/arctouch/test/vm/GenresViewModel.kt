package com.arctouch.test.vm

import android.arch.lifecycle.MutableLiveData
import com.arctouch.test.data.model.Genre
import com.arctouch.test.data.repository.GenreRepository
import com.arctouch.test.schedulers.ISchedulerProvider
import io.reactivex.Observable

class GenresViewModel constructor(
        private val genresRepository: GenreRepository,
        schedulerProvider: ISchedulerProvider
) : BaseViewModel(schedulerProvider) {

    val genres = MutableLiveData<Resource<List<Genre>>>()

    fun fetchGenres(): Observable<List<Genre>> {
        return execute(genresRepository.getGenres(), {
            genres.postValue(Resource.success(it))
        }, {
            genres.postValue(Resource.error(it, null))
        })
    }
}