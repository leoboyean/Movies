package com.glob.movies.domain.usecases

import com.glob.movies.domain.dtos.MovieDto
import com.glob.movies.domain.executors.PostExecutorThread
import com.glob.movies.domain.repositories.MoviesRepository
import com.glob.movies.domain.usecases.movies.GetMovieByIdUseCase
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith( MockitoJUnitRunner.StrictStubs::class)
class GetMovieByIdCaseTest {

    @Mock
    lateinit var moviesRepository: MoviesRepository
    @Mock
    lateinit var postExecutorThread: PostExecutorThread

    val getMovieUseCase: GetMovieByIdUseCase by lazy {
        GetMovieByIdUseCase(moviesRepository, ImmediateExecutorThread(), postExecutorThread)
    }

    @Before
    fun setup() {
        Mockito.`when`(postExecutorThread.getScheduler()).thenReturn(Schedulers.trampoline())
    }

    @Test
    fun validateMovieSuccess() {
        val params = GetMovieByIdUseCase.Params("1")
        val mockMovie = MovieDto("1", "MovieTest", "overview","frontImage")
        Mockito.`when`(moviesRepository.getMovieById("1")).thenReturn(Single.just(mockMovie))
        getMovieUseCase.execute(params)
            .test()
            .assertComplete()
            .assertNoErrors()
            .assertValue {
                it.id == "1"
            }
    }

    @Test
    fun validateGetMovieError() {
        val params = GetMovieByIdUseCase.Params("1")
        val message = "Item not found!"
        Mockito.`when`(moviesRepository.getMovieById("1")).thenReturn(Single.error(Throwable(message)))
        getMovieUseCase.execute(params)
            .test()
            .assertNotComplete()
            .assertError{
                it.message == message
            }
    }

    @Test
    fun validateGetMovieFail() {
        val message = "Invalid Arguments"
        getMovieUseCase.execute(null)
            .test()
            .assertNotComplete()
            .assertError{
                it.message == message
            }
    }

}