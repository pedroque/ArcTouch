package com.arctouch.test.vm

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.lifecycle.Observer
import com.arctouch.test.data.model.Movie
import com.arctouch.test.data.model.Movies
import com.arctouch.test.data.repository.MoviesRepository
import com.arctouch.test.schedulers.TestScheduleProvider
import io.reactivex.Observable
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import java.io.IOException

class SearchMoviesViewModelTest {
    @Rule
    @JvmField
    val rule: TestRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var moviesRepository: MoviesRepository
    @Mock
    private lateinit var observer: Observer<MoviesResource>

    private lateinit var moviesViewModel: SearchMoviesViewModel

    private val firstPageMovies = Movies()

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        moviesViewModel = SearchMoviesViewModel(moviesRepository, TestScheduleProvider())
        firstPageMovies.movies = mutableListOf()
        firstPageMovies.movies.add(Movie())
        firstPageMovies.movies.add(Movie())
        firstPageMovies.movies.add(Movie())
        firstPageMovies.page = 1
        firstPageMovies.totalPages = 10
        val moviesResource = MoviesResource.success(firstPageMovies)
        moviesViewModel.movies.value = moviesResource
        moviesViewModel.query = "query"
    }

    @Test
    fun nextPage() {
        val movies = Movies()
        movies.movies = mutableListOf()
        movies.movies.add(Movie())
        movies.movies.add(Movie())
        movies.movies.add(Movie())
        movies.page = 2
        movies.totalPages = 10
        Mockito.`when`(moviesRepository.getMovies(anyInt(), anyString())).thenReturn(Observable.just(movies))
        moviesViewModel.movies.observeForever(observer)
        moviesViewModel.nextPage()
                .test()
                .assertNoErrors()
                .assertComplete()
                .assertValue(movies)
                .awaitTerminalEvent()
        verify(moviesRepository).getMovies(2, "query")
        verify(observer).onChanged(MoviesResource.loading(firstPageMovies))
        movies.movies.addAll(0, firstPageMovies.movies)
        verify(observer).onChanged(MoviesResource.success(movies))
    }

    @Test
    fun nextPageError() {
        val error = IOException()
        Mockito.`when`(moviesRepository.getMovies(anyInt(), anyString())).thenReturn(Observable.error(error))
        moviesViewModel.movies.observeForever(observer)
        moviesViewModel.nextPage()
                .test()
                .assertError(error)
                .assertNotComplete()
                .assertNoValues()
                .awaitTerminalEvent()
        verify(moviesRepository).getMovies(2, "query")
        verify(observer).onChanged(MoviesResource.loading(firstPageMovies))
        verify(observer).onChanged(MoviesResource.error(error))
    }

}