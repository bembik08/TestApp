package com.geekbrains.tests.utils

import io.reactivex.Scheduler

interface ScheduledProvider {
    fun io() : Scheduler
    fun main() : Scheduler
}