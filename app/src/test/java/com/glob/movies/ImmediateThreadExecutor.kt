package com.glob.movies

import com.glob.movies.domain.executors.ThreadExecutor

class ImmediateThreadExecutor : ThreadExecutor {

    override fun execute(runnable: Runnable) {
        runnable.run()
    }

}