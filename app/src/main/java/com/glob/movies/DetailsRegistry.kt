package com.glob.movies

import com.glob.movies.contracts.DetailsContract
import com.glob.movies.data.executors.JobExecutor
import com.glob.movies.data.executors.UIThread
import com.glob.movies.data.providers.MoviesDataProvider
import com.glob.movies.data.remote.services.MovieServices
import com.glob.movies.data.repositories.MoviesDataRepository
import com.glob.movies.domain.executors.PostExecutorThread
import com.glob.movies.domain.executors.ThreadExecutor
import com.glob.movies.domain.providers.MoviesProvider
import com.glob.movies.domain.repositories.MoviesRepository
import com.glob.movies.presenters.DetailsPresenter
import com.glob.movies.services.RetrofitFactory

class DetailsRegistry {

    private val threadExecutor: ThreadExecutor by lazy {
        JobExecutor()
    }

    private val postExecutorThread: PostExecutorThread by lazy {
        UIThread()
    }

    private fun getMovieService(): MovieServices {
        return RetrofitFactory.getInstance().create(MovieServices::class.java)
    }

    private val moviesRepository: MoviesRepository by lazy {
        MoviesDataRepository(getMovieService())
    }

    private val provider: MoviesProvider by lazy {
        MoviesDataProvider(moviesRepository, threadExecutor, postExecutorThread)
    }

    fun provide(view: DetailsContract.View): DetailsContract.Presenter {
        return DetailsPresenter(provider, view)
    }
}