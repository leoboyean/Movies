package com.glob.movies.contracts

import com.glob.movies.domain.dtos.MovieDto

interface MainMenuContract {

    interface View {
        fun onMoviesLoaded(movies: List<MovieDto>)
        fun onMoviesLoadedFail(message: String)
    }

    interface Presenter {
        fun getMovieList(page: Int)
        fun onDestroy()
    }
}