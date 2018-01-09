package com.arctouch.test.vm

import android.arch.lifecycle.MutableLiveData
import com.arctouch.test.data.model.Movies
import com.arctouch.test.data.repository.MoviesRepository
import com.arctouch.test.exception.EmptyQueryException
import com.arctouch.test.schedulers.ISchedulerProvider
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.Subject
import java.util.concurrent.TimeUnit

class SearchMoviesViewModel constructor(
        private val moviesRepository: MoviesRepository,
        schedulerProvider: ISchedulerProvider
) : BaseViewModel(schedulerProvider) {

    val movies = MutableLiveData<MoviesResource>()

    private var observableQuery: Subject<String> = BehaviorSubject.create()

    var query: String = ""
        set(value) {
            if (field != value) {
                field = value
                observableQuery.onNext(field)
            }
        }

    init {
        setQuery()
    }

    fun nextPage(): Observable<Movies> {
        val nextPage = movies.value?.data?.page?.plus(1) ?: 1
        movies.postValue(MoviesResource.loading(movies.value?.data?.let { it }))
        return execute(moviesRepository.getMovies(nextPage, query), {
            movies.value?.data?.let { previous ->
                it.movies.addAll(0, previous.movies)
            }
            movies.postValue(MoviesResource.success(it))
        }, {
            if (it is EmptyQueryException) {
                movies.postValue(MoviesResource.success(null))
            } else {
                movies.postValue(MoviesResource.error(it))
            }
        })
    }

    private fun setQuery(): Observable<Movies> {
        return execute(observableQuery.debounce(500, TimeUnit.MILLISECONDS)
                .flatMap {
                    movies.postValue(MoviesResource.loading(null))
                    moviesRepository.getMovies(1, it)
                }, {
                    movies.postValue(MoviesResource.success(it))
                }, {
                    if (it is EmptyQueryException) {
                        movies.postValue(MoviesResource.success(null))
                    } else {
                        movies.postValue(MoviesResource.error(it))
                    }
                    setQuery()
                })
    }
}