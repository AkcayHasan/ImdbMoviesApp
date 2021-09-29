package com.example.imdbfilmapp.features.movies.domain.usecases

import com.example.imdbfilmapp.features.movies.data.repository.MovieRepository
import com.example.imdbfilmapp.features.movies.domain.entities.Movie
import com.example.imdbfilmapp.features.movies.domain.entities.Movies
import com.example.imdbfilmapp.features.movies.domain.mapper.toMovie
import com.example.imdbfilmapp.features.movies.domain.mapper.toMovies
import com.example.imdbfilmapp.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class HomeUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {

    suspend fun getAllMovies(
        searchText: String,
        pageNum: Int
    ): Flow<Resource<Movies>> {
        return flow {
            emit(Resource.loading(null))
            val result = movieRepository.getAllMovies(searchText, pageNum)
            result.data?.let {
                emit(Resource.success(it.toMovies()))
            } ?: kotlin.run {
                emit(Resource.error(result.message ?: "", null))
            }
        }.flowOn(Dispatchers.IO)
    }


}