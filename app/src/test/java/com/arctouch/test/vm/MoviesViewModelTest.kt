package com.arctouch.test.vm

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.lifecycle.Observer
import com.arctouch.test.data.model.Config
import com.arctouch.test.data.model.Genre
import com.arctouch.test.data.model.Movie
import com.arctouch.test.data.model.Movies
import com.arctouch.test.data.repository.ConfigRepository
import com.arctouch.test.data.repository.GenreRepository
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
import org.mockito.Mockito.anyInt
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import java.io.IOException

class MoviesViewModelTest {
    @Rule
    @JvmField
    val rule: TestRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var moviesRepository: MoviesRepository
    @Mock
    private lateinit var observer: Observer<Resource<Movies>>

    private lateinit var moviesViewModel: MoviesViewModel

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        moviesViewModel = MoviesViewModel(moviesRepository, TestScheduleProvider())
    }

    @Test
    fun nextPage() {
        val movies = Movies()
        movies.movies = mutableListOf()
        movies.movies.add(Movie())
        movies.movies.add(Movie())
        movies.movies.add(Movie())
        Mockito.`when`(moviesRepository.getMovies(anyInt())).thenReturn(Observable.just(movies))
        moviesViewModel.movies.observeForever(observer)
        moviesViewModel.nextPage()
                .test()
                .assertNoErrors()
                .assertComplete()
                .assertValue(movies)
                .awaitTerminalEvent()
        verify(moviesRepository).getMovies(1)
        verify(observer).onChanged(Resource.loading(null))
        verify(observer).onChanged(Resource.success(movies))
    }

    @Test
    fun fetchGenresError() {
        val error = IOException()
        Mockito.`when`(moviesRepository.getMovies(anyInt())).thenReturn(Observable.error(error))
        moviesViewModel.movies.observeForever(observer)
        moviesViewModel.nextPage()
                .test()
                .assertError(error)
                .assertNotComplete()
                .assertNoValues()
                .awaitTerminalEvent()
        verify(moviesRepository).getMovies(1)
        verify(observer).onChanged(Resource.loading(null))
        verify(observer).onChanged(Resource.error(error, null))
    }

}