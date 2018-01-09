package com.arctouch.test.di.module

import com.arctouch.test.data.entity.MovieEntity
import com.arctouch.test.data.entity.MoviesEntity
import com.arctouch.test.data.entity.mapper.Mapper
import com.arctouch.test.data.entity.mapper.MovieMapper
import com.arctouch.test.data.entity.mapper.MoviesMapper
import com.arctouch.test.data.model.Movie
import com.arctouch.test.data.model.Movies
import com.arctouch.test.data.repository.MoviesRepository
import com.arctouch.test.data.repository.MoviesRepositoryImpl
import com.arctouch.test.data.repository.datasource.CloudMoviesDataSource
import com.arctouch.test.data.repository.datasource.MovieDataSource
import com.arctouch.test.di.ActivityScope
import com.arctouch.test.di.Named
import com.arctouch.test.schedulers.ISchedulerProvider
import com.arctouch.test.vm.MoviesViewModelFactory
import dagger.Module
import dagger.Provides
import dagger.Reusable

@Module
class MovieModule {

    @Provides
    @Reusable
    internal fun provideMovieMapper(movieMapper: MovieMapper): Mapper<Movie, MovieEntity> = movieMapper

    @Provides
    @Reusable
    internal fun provideMoviesMapper(moviesMapper: MoviesMapper): Mapper<Movies, MoviesEntity> = moviesMapper

    @Provides
    @Reusable
    @Named("cloud")
    internal fun provideCloudMoviesDataSource(cloudMoviesDataSource: CloudMoviesDataSource): MovieDataSource = cloudMoviesDataSource

    @Provides
    @Reusable
    internal fun provideMoviesRepository(moviesRepository: MoviesRepositoryImpl): MoviesRepository = moviesRepository

    @Provides
    @ActivityScope
    internal fun provideMoviesViewModelFactory(moviesRepository: MoviesRepository, schedulerProvider: ISchedulerProvider) =
            MoviesViewModelFactory(moviesRepository, schedulerProvider)
}