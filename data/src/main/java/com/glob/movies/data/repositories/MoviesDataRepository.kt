package com.glob.movies.data.repositories

import com.glob.movies.data.remote.services.MovieServices
import com.glob.movies.domain.dtos.MovieDto
import com.glob.movies.domain.repositories.MoviesRepository
import io.reactivex.Single

class MoviesDataRepository(private val moviesServices: MovieServices) : MoviesRepository {

    private val DEFAULTPAGE = 1

    override fun getMovieById(id: String): Single<MovieDto> {
        return moviesServices.getMovieDetails(id.toLong())
            .flatMap { response ->
                return@flatMap if (response.isSuccessful) {
                    response.body()?.let {
                        Single.just(MovieDto(it.id, it.title, it.overview, it.posterPath))
                    }
                } else {
                    Single.error(Throwable(response.errorBody().toString()))
                }
            }
    }

    override fun getMovies(page: Int?): Single<List<MovieDto>> {
        return moviesServices.getPopularMovies(page ?: DEFAULTPAGE)
            .flatMap {response ->
                return@flatMap if (response.isSuccessful) {
                    val movies : ArrayList<MovieDto> = arrayListOf()
                    response.body()?.results?.forEach {
                        movies.add(MovieDto(it.id, it.title, it.overview, it.posterPath))
                    }
                    Single.just(movies)
                } else {
                    Single.error(Throwable(response.message().toString()))
                }
            }
    }
}