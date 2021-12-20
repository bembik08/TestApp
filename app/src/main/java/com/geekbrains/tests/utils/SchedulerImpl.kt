package com.geekbrains.tests.utils

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class SchedulerImpl : ScheduledProvider {
    override fun io(): Scheduler = Schedulers.io()

    override fun main(): Scheduler = AndroidSchedulers.mainThread()
}