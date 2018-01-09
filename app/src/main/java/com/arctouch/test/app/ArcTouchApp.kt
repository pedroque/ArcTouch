package com.arctouch.test.app

import android.app.Activity
import android.app.Application
import com.arctouch.test.di.AppInjector
import com.facebook.common.logging.FLog
import com.facebook.drawee.backends.pipeline.Fresco
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import io.realm.Realm
import io.realm.RealmConfiguration
import javax.inject.Inject


class ArcTouchApp : Application(), HasActivityInjector{
    @Inject lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    override fun activityInjector() = dispatchingAndroidInjector

    override fun onCreate() {
        super.onCreate()
        Fresco.initialize(this)
        AppInjector.init(this)
        Realm.init(this)
        Realm.setDefaultConfiguration(RealmConfiguration.Builder()
                .deleteRealmIfMigrationNeeded()
                .build())
    }
}