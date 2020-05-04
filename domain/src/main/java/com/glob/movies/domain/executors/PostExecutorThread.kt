package com.glob.movies.domain.executors

import io.reactivex.Scheduler

interface PostExecutorThread {
    fun getScheduler() : Scheduler
}