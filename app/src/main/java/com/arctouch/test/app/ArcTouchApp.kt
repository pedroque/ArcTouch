package com.arctouch.test.app

import android.app.Activity
import android.app.Application
import com.arctouch.test.di.AppInjector
import com.facebook.drawee.backends.pipeline.Fresco
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject


class ArcTouchApp : Application(), HasActivityInjector{
    @Inject lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    override fun activityInjector() = dispatchingAndroidInjector

    override fun onCreate() {
        super.onCreate()
        Fresco.initialize(this)
        AppInjector.init(this)
    }
}