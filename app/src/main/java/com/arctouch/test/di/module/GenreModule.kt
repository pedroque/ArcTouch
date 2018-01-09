package com.arctouch.test.di.module

import com.arctouch.test.data.cache.Cache
import com.arctouch.test.data.cache.GenreCache
import com.arctouch.test.data.entity.GenreEntity
import com.arctouch.test.data.entity.mapper.GenreMapper
import com.arctouch.test.data.entity.mapper.Mapper
import com.arctouch.test.data.model.Genre
import com.arctouch.test.data.repository.GenreRepository
import com.arctouch.test.data.repository.GenreRepositoryImpl
import com.arctouch.test.data.repository.datasource.CacheGenreDataSource
import com.arctouch.test.data.repository.datasource.CloudGenreDataSource
import com.arctouch.test.data.repository.datasource.GenreDataSource
import com.arctouch.test.di.ActivityScope
import com.arctouch.test.di.Named
import com.arctouch.test.schedulers.ISchedulerProvider
import com.arctouch.test.vm.GenreViewModelFactory
import dagger.Module
import dagger.Provides
import dagger.Reusable

@Module
class GenreModule {
    @Provides
    @Reusable
    internal fun provideGenreCache(genreCache: GenreCache): Cache<List<Genre>> = genreCache

    @Provides
    @Reusable
    internal fun provideGenreMapper(genreMapper: GenreMapper): Mapper<Genre, GenreEntity> = genreMapper

    @Provides
    @Reusable
    @Named("cloud")
    internal fun provideCloudGenreDataSource(cloudGenreDataSource: CloudGenreDataSource): GenreDataSource = cloudGenreDataSource

    @Provides
    @Reusable
    @Named("cache")
    internal fun provideCacheGenreDataSource(cacheGenreDataSource: CacheGenreDataSource): GenreDataSource = cacheGenreDataSource

    @Provides
    @Reusable
    @Named("genres")
    internal fun provideGenresCache(genresCache: GenreCache): Cache<List<Genre>> = genresCache

    @Provides
    @Reusable
    internal fun provideGenreRepository(genreRepository: GenreRepositoryImpl): GenreRepository = genreRepository

    @Provides
    @ActivityScope
    internal fun provideGenreViewModelFactory(genreRepository: GenreRepository, schedulerProvider: ISchedulerProvider) =
            GenreViewModelFactory(genreRepository, schedulerProvider)
}