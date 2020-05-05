package com.glob.movies.data.providers

import com.glob.movies.domain.dtos.MovieDto
import com.glob.movies.domain.executors.PostExecutorThread
import com.glob.movies.domain.executors.ThreadExecutor
import com.glob.movies.domain.providers.MoviesProvider
import com.glob.movies.domain.repositories.MoviesRepository
import com.glob.movies.domain.usecases.SingleUseCase
import com.glob.movies.domain.usecases.movies.GetMovieByIdUseCase
import com.glob.movies.domain.usecases.movies.GetMoviesUseCase

class MoviesDataProvider(private val moviesRepository: MoviesRepository,
                         private val threadExecutor: ThreadExecutor,
                         private val postExecutorThread: PostExecutorThread) : MoviesProvider {
    override fun getMovieByIdUseCase(): SingleUseCase<GetMovieByIdUseCase.Params, MovieDto> {
        return GetMovieByIdUseCase(moviesRepository, threadExecutor, postExecutorThread)
    }

    override fun getMoviesUseCase(): SingleUseCase<GetMovieByIdUseCase.Params, List<MovieDto>> {
        return GetMoviesUseCase(moviesRepository, threadExecutor, postExecutorThread)
    }
}