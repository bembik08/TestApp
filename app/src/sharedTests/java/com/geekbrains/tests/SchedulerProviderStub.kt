package com.geekbrains.tests

import com.geekbrains.tests.utils.ScheduledProvider
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers

class SchedulerProviderStub: ScheduledProvider {
    override fun io(): Scheduler = Schedulers.trampoline()

    override fun main(): Scheduler = Schedulers.trampoline()
}