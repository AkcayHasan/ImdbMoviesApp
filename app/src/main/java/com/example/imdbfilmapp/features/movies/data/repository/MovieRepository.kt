package com.example.imdbfilmapp.features.movies.data.repository

import com.example.imdbfilmapp.base.BaseServiceImpl
import com.example.imdbfilmapp.features.movies.data.api.MovieApi
import com.example.imdbfilmapp.features.movies.data.entities.MoviesResponse
import com.example.imdbfilmapp.features.movies.data.entities.SelectedMovieResponse
import com.example.imdbfilmapp.features.movies.domain.repository.IMovieRepository
import com.example.imdbfilmapp.util.Constants.API_KEY
import com.example.imdbfilmapp.util.Resource
import retrofit2.Retrofit
import javax.inject.Inject

class MovieRepository @Inject constructor(
    private val retrofit: Retrofit
) : IMovieRepository, BaseServiceImpl() {

    private val movieApi: MovieApi by lazy {
        retrofit.create(MovieApi::class.java)
    }

    override suspend fun getAllMovies(
        searchText: String,
        pageNum: Int
    ): Resource<MoviesResponse> {
        return getResponse(request = { movieApi.getMovies(searchText, pageNum) })
    }

    override suspend fun getMovie(imdbId: String): Resource<SelectedMovieResponse> {
        return getResponse(request = { movieApi.getMovie(imdbId) })
    }
}