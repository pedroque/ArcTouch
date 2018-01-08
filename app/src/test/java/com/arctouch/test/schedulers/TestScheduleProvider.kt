package com.arctouch.test.schedulers

import io.reactivex.schedulers.Schedulers


class TestScheduleProvider : ISchedulerProvider {

    override fun computation() = Schedulers.trampoline()

    override fun ui() = Schedulers.trampoline()
}