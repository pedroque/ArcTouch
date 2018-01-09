/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.arctouch.test.di.module

import com.arctouch.test.di.ActivityScope
import com.arctouch.test.ui.activity.MovieActivity
import com.arctouch.test.ui.activity.MoviesActivity
import com.arctouch.test.ui.activity.SearchMovieActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {

    @ActivityScope
    @ContributesAndroidInjector(modules = [(ConfigModule::class), (GenreModule::class), (MovieModule::class)])
    internal abstract fun contributeMoviesActivity(): MoviesActivity

    @ActivityScope
    @ContributesAndroidInjector(modules = [(ConfigModule::class), (GenreModule::class), (MovieModule::class)])
    internal abstract fun contributeMovieActivity(): MovieActivity

    @ActivityScope
    @ContributesAndroidInjector(modules = [(ConfigModule::class), (GenreModule::class), (MovieModule::class)])
    internal abstract fun contributeSearchMovieActivity(): SearchMovieActivity

}
