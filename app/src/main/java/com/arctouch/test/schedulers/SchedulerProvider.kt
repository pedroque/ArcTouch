package com.arctouch.test.schedulers

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class SchedulerProvider @Inject constructor() : ISchedulerProvider {
    override fun computation() = Schedulers.computation()

    override fun ui() = AndroidSchedulers.mainThread()!!
}