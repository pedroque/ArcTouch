package com.arctouch.test.schedulers

import io.reactivex.Scheduler

interface ISchedulerProvider {
    fun computation() : Scheduler
    fun ui() : Scheduler
}