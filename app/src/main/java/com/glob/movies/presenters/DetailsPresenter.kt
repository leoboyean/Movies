package com.glob.movies.presenters

import com.glob.movies.contracts.DetailsContract
import com.glob.movies.domain.providers.MoviesProvider
import com.glob.movies.domain.usecases.movies.GetMovieByIdUseCase
import io.reactivex.disposables.CompositeDisposable

class DetailsPresenter(
    private val moviesProvider: MoviesProvider,
    private val view: DetailsContract.View
) : DetailsContract.Presenter {

    private val disposable = CompositeDisposable()

    override fun getMovie(id: String) {
        val params = GetMovieByIdUseCase.Params(id)
        disposable.add(
            moviesProvider.getMovieByIdUseCase().execute(params)
                .subscribe({success ->
                    view.onMovieLoaded(success)
                }, { error ->
                    view.onMovieLoadedFail(error.message ?: "")
                })
        )
    }

    override fun onDestroy() {
        disposable.dispose()
    }

}