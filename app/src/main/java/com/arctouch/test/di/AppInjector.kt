package com.arctouch.test.di

import com.arctouch.test.app.ArcTouchApp
import com.arctouch.test.di.component.DaggerAppComponent
import com.arctouch.test.di.module.AppModule

object AppInjector {
    @JvmStatic
    fun init(app: ArcTouchApp) {
        DaggerAppComponent.builder()
                .application(app)
                .appModule(AppModule(app))
                .build()
                .inject(app)
    }
}
