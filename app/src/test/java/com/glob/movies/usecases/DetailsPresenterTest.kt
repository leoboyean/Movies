package com.glob.movies.usecases

import com.glob.movies.ImmediateThreadExecutor
import com.glob.movies.contracts.DetailsContract
import com.glob.movies.domain.dtos.MovieDto
import com.glob.movies.domain.executors.PostExecutorThread
import com.glob.movies.domain.providers.MoviesProvider
import com.glob.movies.domain.repositories.MoviesRepository
import com.glob.movies.domain.usecases.movies.GetMovieByIdUseCase
import com.glob.movies.presenters.DetailsPresenter
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner.StrictStubs::class)
class DetailsPresenterTest {

    @Mock
    lateinit var view: DetailsContract.View

    @Mock
    lateinit var repository: MoviesRepository

    @Mock
    lateinit var moviesProvider: MoviesProvider

    private val presenter: DetailsPresenter by lazy {
        DetailsPresenter(moviesProvider, view)
    }

    @Mock
    private lateinit var postExecutorThread: PostExecutorThread

    @Before
    fun setup() {
        Mockito.`when`(postExecutorThread.getScheduler()).thenReturn(Schedulers.trampoline())
    }

    @Test
    fun validateGetMoviesSuccess() {
        val id = "1"
        val movie = MovieDto("1", "TestA", "TestB", "imageA")

        Mockito.`when`(repository.getMovieById(id)).thenReturn(Single.just(movie))
        Mockito.`when`(moviesProvider.getMovieByIdUseCase())
            .thenReturn(
                GetMovieByIdUseCase(
                    repository,
                    ImmediateThreadExecutor(),
                    postExecutorThread
                )
            )

        presenter.getMovie(id)
        Mockito.verify(view).onMovieLoaded(movie)
    }

    @Test
    fun validateGetMoviesFail() {
        val id = "1"
        val message = "Not Found"
        Mockito.`when`(repository.getMovieById(id)).thenReturn(Single.error(Throwable(message)))
        Mockito.`when`(moviesProvider.getMovieByIdUseCase())
            .thenReturn(
                GetMovieByIdUseCase(
                    repository,
                    ImmediateThreadExecutor(),
                    postExecutorThread
                )
            )
        presenter.getMovie(id)
        Mockito.verify(view).onMovieLoadedFail(message)
    }
}