package com.example.imdbfilmapp.features.movies.domain.entities

data class Movie(
    val title: String,
    val imdbId: String,
    val year: String,
    val poster: String
)
