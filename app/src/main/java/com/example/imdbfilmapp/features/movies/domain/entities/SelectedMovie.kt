package com.example.imdbfilmapp.features.movies.domain.entities

import com.example.imdbfilmapp.features.movies.data.entities.RatingResponse

data class SelectedMovie(
    val actors: String,
    val country: String,
    val director: String,
    val genre: String,
    val language: String,
    val metaScore: String,
    val plot: String,
    val poster: String,
    val ratingResponse: List<Rating>,
    val released: String,
    val runtime: String,
    val title: String,
    val year: String,
    val imdbRating: String,
    val imdbVotes: String
)
