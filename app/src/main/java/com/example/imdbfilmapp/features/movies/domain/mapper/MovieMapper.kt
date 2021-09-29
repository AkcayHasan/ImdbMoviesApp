package com.example.imdbfilmapp.features.movies.domain.mapper

import com.example.imdbfilmapp.features.movies.data.entities.MoviesResponse
import com.example.imdbfilmapp.features.movies.data.entities.MovieResponse
import com.example.imdbfilmapp.features.movies.data.entities.RatingResponse
import com.example.imdbfilmapp.features.movies.data.entities.SelectedMovieResponse
import com.example.imdbfilmapp.features.movies.domain.entities.Movies
import com.example.imdbfilmapp.features.movies.domain.entities.Movie
import com.example.imdbfilmapp.features.movies.domain.entities.Rating
import com.example.imdbfilmapp.features.movies.domain.entities.SelectedMovie

fun MoviesResponse.toMovies() = Movies(
    this.movies?.map {
        it.toMovie()
    },
    this.response,
    this.error
)

fun MovieResponse.toMovie() = Movie(
    this.title,
    this.imdbId,
    this.year,
    this.poster
)


fun SelectedMovieResponse.toSelectedMovie() = SelectedMovie(
    this.actors,
    this.country,
    this.director,
    this.genre,
    this.language,
    this.metaScore,
    this.plot,
    this.poster,
    this.ratingResponse.map {
        it.toRating()
    },
    this.released,
    this.runtime,
    this.title,
    this.imdbRating,
    this.imdbVotes
)

fun RatingResponse.toRating() = Rating(
    this.source,
    this.value
)