package com.glob.movies.domain.repositories

import com.glob.movies.domain.dtos.MovieDto
import io.reactivex.Single

interface MoviesRepository {
    fun getMovieById(id: String): Single<MovieDto>
    fun getMovies(page: Int? = null): Single<List<MovieDto>>
}