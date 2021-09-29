package com.example.imdbfilmapp.features.movies.domain.entities


data class Movies(
    val movies: List<Movie>?,
    val response: String,
    val error: String?
)
