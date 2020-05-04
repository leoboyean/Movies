package com.glob.movies.domain.usecases.movies

import com.glob.movies.domain.dtos.MovieDto
import com.glob.movies.domain.executors.PostExecutorThread
import com.glob.movies.domain.executors.ThreadExecutor
import com.glob.movies.domain.repositories.MoviesRepository
import com.glob.movies.domain.usecases.SingleUseCase
import io.reactivex.Single

class GetMoviesUseCase(
    private val moviesRepository: MoviesRepository,
    threadExecutor: ThreadExecutor,
    postExecutorThread: PostExecutorThread
) : SingleUseCase<GetMovieByIdUseCase.Params, List<MovieDto>>(threadExecutor, postExecutorThread) {

    override fun buildUSingleUseCase(params: GetMovieByIdUseCase.Params?): Single<List<MovieDto>> {
        return params?.let {
            moviesRepository.getMovies(it.id.toInt())
        } ?: Single.error(Throwable("Invalid Arguments"))
    }
}