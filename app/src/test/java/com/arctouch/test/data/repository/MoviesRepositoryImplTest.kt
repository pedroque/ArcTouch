package com.arctouch.test.data.repository

import com.arctouch.test.data.repository.datasource.MovieDataSource
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

class MoviesRepositoryImplTest {
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
        moviesRepository.getMovies(1)
        verify(cloudMoviesDataSource).getUpcomingMovies(1)
    }

}