package com.arctouch.test.di

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class NamedString(val value: String = "")
