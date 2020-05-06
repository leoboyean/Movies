package com.glob.movies.data.remote.requests

import com.glob.movies.data.remote.responses.GetMovieResponse
import com.google.gson.annotations.SerializedName

class ObjectMovies(
    @SerializedName("page") val page : Int,
    @SerializedName("total_results") val total_results: Long,
    @SerializedName("total_pages") val total_pages: Int,
    @SerializedName("results") val results: List<GetMovieResponse>
) {
}