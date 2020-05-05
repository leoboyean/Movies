package com.glob.movies.presenters

import com.glob.movies.contracts.MainMenuContract
import com.glob.movies.domain.providers.MoviesProvider
import com.glob.movies.domain.usecases.movies.GetMovieByIdUseCase
import io.reactivex.disposables.CompositeDisposable

class MainMenuPresenter(
    private val moviesProvider: MoviesProvider,
    private val view: MainMenuContract.View
) : MainMenuContract.Presenter {

    private val disposable = CompositeDisposable()

    override fun getMovieList(page: Int) {
        val params = GetMovieByIdUseCase.Params("$page")
        disposable.add(
            moviesProvider.getMoviesUseCase().execute(params)
                .subscribe({ success ->
                    view.onMoviesLoaded(success)
                }, { error ->
                    view.onMoviesLoadedFail(error.message.toString())
                })
        )
    }

    override fun onDestroy() {
        disposable.dispose()
    }
}