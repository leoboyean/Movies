package com.glob.movies.usecases

import com.glob.movies.ImmediateThreadExecutor
import com.glob.movies.contracts.MainMenuContract
import com.glob.movies.domain.dtos.MovieDto
import com.glob.movies.domain.executors.PostExecutorThread
import com.glob.movies.domain.providers.MoviesProvider
import com.glob.movies.domain.repositories.MoviesRepository
import com.glob.movies.domain.usecases.movies.GetMoviesUseCase
import com.glob.movies.presenters.MainMenuPresenter
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner.StrictStubs::class)
class MainMenuPresenterTest {

    @Mock
    lateinit var view: MainMenuContract.View

    @Mock
    lateinit var repository: MoviesRepository

    @Mock
    lateinit var moviesProvider: MoviesProvider

    private val presenter: MainMenuPresenter by lazy {
        MainMenuPresenter(moviesProvider, view)
    }

    @Mock
    private lateinit var postExecutorThread: PostExecutorThread

    @Before
    fun setup() {
        Mockito.`when`(postExecutorThread.getScheduler()).thenReturn(Schedulers.trampoline())
    }

    @Test
    fun validateGetMoviesSuccess() {
        val page = 1
        val movie = MovieDto("1", "TestA", "TestB", "imageA")

        Mockito.`when`(repository.getMovies(page)).thenReturn(Single.just(listOf(movie)))
        Mockito.`when`(moviesProvider.getMoviesUseCase())
            .thenReturn(
                GetMoviesUseCase(
                    repository,
                    ImmediateThreadExecutor(),
                    postExecutorThread
                )
            )
        presenter.getMovieList(page)
        Mockito.verify(view).onMoviesLoaded(listOf(movie))
    }

    @Test
    fun validateGetMoviesFail() {
        val message = "Not Found"
        Mockito.`when`(repository.getMovies(1)).thenReturn(Single.error(Throwable(message)))
        Mockito.`when`(moviesProvider.getMoviesUseCase())
            .thenReturn(
                GetMoviesUseCase(
                    repository,
                    ImmediateThreadExecutor(),
                    postExecutorThread
                )
            )
        presenter.getMovieList(1)
        Mockito.verify(view).onMoviesLoadedFail(message)
    }
}