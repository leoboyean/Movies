package com.glob.movies.data.remote.responses

import com.google.gson.annotations.SerializedName

data class GetMovieResponse(
    @SerializedName("id") val id: String,
    @SerializedName("title") val title: String,
    @SerializedName("overview") val overview: String,
    @SerializedName("poster_path") val posterPath: String
)