package com.example.imdbfilmapp.features.movies.data.api

import com.example.imdbfilmapp.features.movies.data.entities.MoviesResponse
import com.example.imdbfilmapp.features.movies.data.entities.SelectedMovieResponse
import com.example.imdbfilmapp.util.Constants.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApi {

    @GET("?apikey=$API_KEY")
    suspend fun getMovies(
        @Query("s") searchText: String,
        @Query("page") pageNum: Int
    ): Response<MoviesResponse>


    @GET("?apikey=$API_KEY")
    suspend fun getMovie(
        @Query("i") imdbId: String,
    ): Response<SelectedMovieResponse>

}