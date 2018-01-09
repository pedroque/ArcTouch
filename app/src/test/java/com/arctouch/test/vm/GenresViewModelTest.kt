package com.arctouch.test.vm

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.lifecycle.Observer
import com.arctouch.test.data.model.Genre
import com.arctouch.test.data.repository.GenreRepository
import com.arctouch.test.schedulers.TestScheduleProvider
import io.reactivex.Observable
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import java.io.IOException

class GenresViewModelTest {
    @Rule
    @JvmField
    val rule: TestRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var genreRepository: GenreRepository
    @Mock
    private lateinit var observer: Observer<Resource<List<Genre>>>

    private lateinit var genresViewModel: GenresViewModel

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        genresViewModel = GenresViewModel(genreRepository, TestScheduleProvider())
    }

    @Test
    fun fetchGenres() {
        val genres = mutableListOf<Genre>()
        genres.add(Genre(1, "Action"))
        genres.add(Genre(2, "Drama"))
        genres.add(Genre(3, "Sci-Fi"))
        genres.add(Genre(4, "Horror"))
        Mockito.`when`(genreRepository.getGenres()).thenReturn(Observable.just(genres))
        genresViewModel.genres.observeForever(observer)
        genresViewModel.fetchGenres()
                .test()
                .assertNoErrors()
                .assertComplete()
                .assertValue(genres)
                .awaitTerminalEvent()
        verify(observer).onChanged(Resource.success(genres))
    }

    @Test
    fun fetchGenresError() {
        val error = IOException()
        Mockito.`when`(genreRepository.getGenres()).thenReturn(Observable.error(error))
        genresViewModel.genres.observeForever(observer)
        genresViewModel.fetchGenres()
                .test()
                .assertError(error)
                .assertNotComplete()
                .assertNoValues()
                .awaitTerminalEvent()
        verify(observer).onChanged(Resource.error(error, null))
    }

}