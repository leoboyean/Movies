package com.glob.movies.domain.providers

import com.glob.movies.domain.dtos.MovieDto
import com.glob.movies.domain.usecases.SingleUseCase
import com.glob.movies.domain.usecases.movies.GetMovieByIdUseCase

interface MoviesProvider {
    fun getMovieByIdUseCase() : SingleUseCase<GetMovieByIdUseCase.Params, MovieDto>
    fun getMoviesUseCase() : SingleUseCase<GetMovieByIdUseCase.Params, List<MovieDto>>
}