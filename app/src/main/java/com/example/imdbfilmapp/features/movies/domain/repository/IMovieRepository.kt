package com.example.imdbfilmapp.features.movies.domain.repository

import com.example.imdbfilmapp.features.movies.data.entities.MoviesResponse
import com.example.imdbfilmapp.features.movies.data.entities.SelectedMovieResponse
import com.example.imdbfilmapp.util.Resource

interface IMovieRepository {

    suspend fun getAllMovies(searchText: String, pageNum: Int): Resource<MoviesResponse>
    suspend fun getMovie(imdbId: String): Resource<SelectedMovieResponse>

}