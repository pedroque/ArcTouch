package com.arctouch.test.data.repository

import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.arctouch.test.data.cache.Cache
import com.arctouch.test.data.model.Genre
import com.arctouch.test.data.repository.datasource.GenreDataSource
import com.arctouch.test.data.repository.datasource.MovieDataSource
import io.reactivex.Observable
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

class GenresRepositoryImplTest {
    @Rule
    @JvmField
    val rule: TestRule = InstantTaskExecutorRule()

    @Mock
    lateinit var cloudGenresDataSource: GenreDataSource
    @Mock
    lateinit var cacheGenresDataSource: GenreDataSource
    @Mock
    lateinit var genresCache: Cache<List<Genre>>

    lateinit var genresRepository: GenreRepository

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        genresRepository = GenreRepositoryImpl(genresCache, cloudGenresDataSource, cacheGenresDataSource)
    }

    @Test
    fun getValidCache() {
        Mockito.`when`(genresCache.isExpired()).thenReturn(false)
        val genres = mutableListOf<Genre>()
        genres.add(Genre(1, "Action"))
        genres.add(Genre(2, "Drama"))
        genres.add(Genre(3, "Sci-Fi"))
        genres.add(Genre(4, "Horror"))
        Mockito.`when`(cacheGenresDataSource.getGenres()).thenReturn(Observable.just(genres))
        genresRepository.getGenres()
                .test()
                .assertNoErrors()
                .assertValue(genres)
                .assertComplete()
                .awaitTerminalEvent()
        verify(cacheGenresDataSource).getGenres()
    }

    @Test
    fun getExpiredCache() {
        Mockito.`when`(genresCache.isExpired()).thenReturn(true)
        val genres = mutableListOf<Genre>()
        genres.add(Genre(1, "Action"))
        genres.add(Genre(2, "Drama"))
        genres.add(Genre(3, "Sci-Fi"))
        genres.add(Genre(4, "Horror"))
        Mockito.`when`(cloudGenresDataSource.getGenres()).thenReturn(Observable.just(genres))
        genresRepository.getGenres()
                .test()
                .assertNoErrors()
                .assertValue(genres)
                .assertComplete()
                .awaitTerminalEvent()
        verify(cloudGenresDataSource).getGenres()
        verify(genresCache).save(genres)
    }

}