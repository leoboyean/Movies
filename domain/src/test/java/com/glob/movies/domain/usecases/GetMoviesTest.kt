package com.glob.movies.domain.usecases

import com.glob.movies.domain.dtos.MovieDto
import com.glob.movies.domain.executors.PostExecutorThread
import com.glob.movies.domain.repositories.MoviesRepository
import com.glob.movies.domain.usecases.movies.GetMovieByIdUseCase
import com.glob.movies.domain.usecases.movies.GetMoviesUseCase
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner.StrictStubs::class)
class GetMoviesTest {

    @Mock
    lateinit var moviesRepository: MoviesRepository

    @Mock
    lateinit var postExecutorThread: PostExecutorThread

    val getMovieUseCase: GetMoviesUseCase by lazy {
        GetMoviesUseCase(moviesRepository, ImmediateExecutorThread(), postExecutorThread)
    }

    @Before
    fun setup() {
        Mockito.`when`(postExecutorThread.getScheduler()).thenReturn(Schedulers.trampoline())
    }

    @Test
    fun validateGetMoviesSuccess() {
        val params = GetMovieByIdUseCase.Params("1")
        val page = 1
        val moviewMock = MovieDto("1", "MovieTest", "override", "imageTest")
        Mockito.`when`(moviesRepository.getMovies(page)).thenReturn(Single.just(listOf(moviewMock)))
        getMovieUseCase.execute(params)
            .test()
            .assertComplete()
            .assertNoErrors()
            .assertValue {
                if (it.isNotEmpty()) {
                    it.first().id == params.id
                } else
                    false
            }
    }

    @Test
    fun validateGetMoviesError() {
        val params = GetMovieByIdUseCase.Params("1")
        val page = 1
        val message = "Item not found!"
        Mockito.`when`(moviesRepository.getMovies(page))
            .thenReturn(Single.error(Throwable(message)))
        getMovieUseCase.execute(params)
            .test()
            .assertNotComplete()
            .assertError {
                it.message == message
            }
    }


    @Test
    fun validateGetMoviesFail() {
        val message = "Invalid Arguments"
        getMovieUseCase.execute(null)
            .test()
            .assertNotComplete()
            .assertError {
                it.message == message
            }
    }
}