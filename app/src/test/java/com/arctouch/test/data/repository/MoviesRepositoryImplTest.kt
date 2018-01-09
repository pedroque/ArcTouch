package com.arctouch.test.data.repository

import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.arctouch.test.data.model.Movies
import com.arctouch.test.data.repository.datasource.MovieDataSource
import com.arctouch.test.exception.EmptyQueryException
import io.reactivex.Observable
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import java.io.IOException

class MoviesRepositoryImplTest {
    @Rule
    @JvmField
    val rule: TestRule = InstantTaskExecutorRule()

    @Mock
    lateinit var cloudMoviesDataSource: MovieDataSource

    lateinit var moviesRepository: MoviesRepository

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        moviesRepository = MoviesRepositoryImpl(cloudMoviesDataSource)
    }

    @Test
    fun getMovies() {
        val movies = Movies()
        `when`(cloudMoviesDataSource.getUpcomingMovies(anyInt())).thenReturn(Observable.just(movies))
        moviesRepository.getMovies(1)
                .test()
                .assertNoErrors()
                .assertComplete()
                .assertValue(movies)
                .awaitTerminalEvent()
        verify(cloudMoviesDataSource).getUpcomingMovies(1)
    }

    @Test
    fun getMoviesError() {
        `when`(cloudMoviesDataSource.getUpcomingMovies(anyInt())).thenReturn(Observable.error(IOException()))
        moviesRepository.getMovies(1)
                .test()
                .assertNotComplete()
                .assertNoValues()
                .assertError(IOException::class.java)
                .awaitTerminalEvent()
        verify(cloudMoviesDataSource).getUpcomingMovies(1)
    }

    @Test
    fun searchMovies() {
        val movies = Movies()
        `when`(cloudMoviesDataSource.searchMovie(anyInt(), anyString())).thenReturn(Observable.just(movies))
        moviesRepository.getMovies(1, "query")
                .test()
                .assertNoErrors()
                .assertComplete()
                .assertValue(movies)
                .awaitTerminalEvent()
        verify(cloudMoviesDataSource).searchMovie(1, "query")
    }

    @Test
    fun searchMoviesError() {
        `when`(cloudMoviesDataSource.searchMovie(anyInt(), anyString())).thenReturn(Observable.error(IOException()))
        moviesRepository.getMovies(1, "query")
                .test()
                .assertNotComplete()
                .assertNoValues()
                .assertError(IOException::class.java)
                .awaitTerminalEvent()
        verify(cloudMoviesDataSource).searchMovie(1, "query")
    }

    @Test
    fun searchMoviesEmptyQuery() {
        moviesRepository.getMovies(1, "")
                .test()
                .assertNotComplete()
                .assertNoValues()
                .assertError(EmptyQueryException::class.java)
                .awaitTerminalEvent()
    }
}