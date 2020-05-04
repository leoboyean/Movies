package com.glob.movies.domain.usecases

import com.glob.movies.domain.executors.PostExecutorThread
import com.glob.movies.domain.executors.ThreadExecutor
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

abstract class SingleUseCase<INPUT, OUTPUT : Any>(
    private val threadExecutor: ThreadExecutor,
    private val postExecutorThread: PostExecutorThread
) {
    abstract fun buildUSingleUseCase(params: INPUT?): Single<OUTPUT>

    fun execute(params: INPUT?): Single<OUTPUT> {
        return this.buildUSingleUseCase(params).subscribeOn(Schedulers.from(threadExecutor))
            .observeOn(postExecutorThread.getScheduler())
    }
}
