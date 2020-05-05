package com.glob.movies.contracts

import com.glob.movies.domain.dtos.MovieDto

interface DetailsContract {

    interface View {
        fun onMovieLoaded(movieDto: MovieDto)
        fun onMovieLoadedFail(message: String)
    }

    interface Presenter {
        fun getMovie(id: String)
        fun onDestroy()
    }
}