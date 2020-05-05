package com.glob.movies.data.remote.services

import com.glob.movies.data.remote.responses.GetMovieResponse
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieServices {

    @GET("movie/{id}?append_to_response=videos,credits,reviews")
    fun getMovieDetails(@Path("id") id: Long): Single<Response<GetMovieResponse>>

    @GET("movie/popular")
    fun getPopularMovies(@Query("page") page: Int): Single<Response<List<GetMovieResponse>>>

}