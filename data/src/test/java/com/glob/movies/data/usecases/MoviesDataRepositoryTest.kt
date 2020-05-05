package com.glob.movies.data.usecases

import com.glob.movies.data.remote.responses.GetMovieResponse
import com.glob.movies.data.remote.services.MovieServices
import com.glob.movies.data.repositories.MoviesDataRepository
import io.reactivex.Single
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Response

@RunWith(MockitoJUnitRunner.StrictStubs::class)
class MoviesDataRepositoryTest {

    @Mock
    lateinit var movieServices: MovieServices

    private val getMoviesCase: MoviesDataRepository by lazy {
        MoviesDataRepository(movieServices)
    }

    @Test
    fun validateGetMovieDetailSuccess() {
        val idPath = 2L
        val moviewMock = GetMovieResponse("2", "Title", "test", "posterPAth")
        val respose: Response<GetMovieResponse> = Response.success(moviewMock)

        Mockito.`when`(movieServices.getMovieDetails(idPath)).thenReturn(Single.just(respose))

        getMoviesCase.getMovieById("$idPath")
            .test()
            .assertComplete()
            .assertNoErrors()
            .assertValue {
                moviewMock.id.toLong() == idPath
            }
    }

    @Test
    fun validateGetMovieDetailError() {
        val idPath = 36L
        val message = "Detais not found"

        Mockito.`when`(movieServices.getMovieDetails(idPath))
            .thenReturn(Single.error(Throwable(message)))

        getMoviesCase.getMovieById("$idPath")
            .test()
            .assertNotComplete()
            .assertError {
                it.message == message
            }
    }

    @Test
    fun validateGetMoviesForPageSuccess() {
        val movieDetail = GetMovieResponse("3", "titleA", "testOVerview", "porterImg3")
        val response = Response.success(listOf(movieDetail))
        Mockito.`when`(movieServices.getPopularMovies(1))
            .thenReturn(Single.just(response))

        getMoviesCase.getMovies(null)
            .test()
            .assertComplete()
            .assertNoErrors()
            .assertValue {
                if (it.isNotEmpty()) it.first().id == "3" else false
            }
    }

    @Test
    fun validateGetMoviesForPageError() {
        val message = "Items not founf"
        Mockito.`when`(movieServices.getPopularMovies(1))
            .thenReturn(Single.error(Throwable(message)))

        getMoviesCase.getMovies(null)
            .test()
            .assertNotComplete()
            .assertError {
                it.message == message
            }
    }

}