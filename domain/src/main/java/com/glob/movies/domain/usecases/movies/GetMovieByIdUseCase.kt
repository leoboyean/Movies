package com.glob.movies.domain.usecases.movies

import com.glob.movies.domain.dtos.MovieDto
import com.glob.movies.domain.executors.PostExecutorThread
import com.glob.movies.domain.executors.ThreadExecutor
import com.glob.movies.domain.repositories.MoviesRepository
import com.glob.movies.domain.usecases.SingleUseCase
import io.reactivex.Single

class GetMovieByIdUseCase(
    private val moviesRepository: MoviesRepository,
    threadExecutor: ThreadExecutor,
    postExecutorThread: PostExecutorThread
) : SingleUseCase<GetMovieByIdUseCase.Params, MovieDto>(threadExecutor, postExecutorThread){

    data class Params(val id: String)

    override fun buildUSingleUseCase(params: Params?): Single<MovieDto> {
        return params?.let {
            moviesRepository.getMovieById(it.id)
        } ?: Single.error(Throwable("Invalid Arguments"))
    }


}