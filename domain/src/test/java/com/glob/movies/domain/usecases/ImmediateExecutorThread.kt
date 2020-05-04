package com.glob.movies.domain.usecases

import com.glob.movies.domain.executors.ThreadExecutor

class ImmediateExecutorThread : ThreadExecutor {

    override fun execute(runnable: Runnable) {
        runnable.run()
    }
}