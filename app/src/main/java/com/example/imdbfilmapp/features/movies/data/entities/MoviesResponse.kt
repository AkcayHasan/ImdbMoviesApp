package com.example.imdbfilmapp.features.movies.data.entities

import com.google.gson.annotations.SerializedName

data class MoviesResponse(
    @SerializedName("Search")
    val movies: List<MovieResponse>?,
    @SerializedName("totalResults")
    val totalResults: String?,
    @SerializedName("Response")
    val response: String,
    @SerializedName("Error")
    val error: String?
)