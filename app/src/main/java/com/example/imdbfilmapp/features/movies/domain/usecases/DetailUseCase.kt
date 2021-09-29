package com.example.imdbfilmapp.features.movies.domain.usecases

import com.example.imdbfilmapp.features.movies.data.repository.MovieRepository
import com.example.imdbfilmapp.features.movies.domain.entities.SelectedMovie
import com.example.imdbfilmapp.features.movies.domain.mapper.toSelectedMovie
import com.example.imdbfilmapp.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class DetailUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {

    suspend fun getSelectedMovie(
        imdbId: String
    ): Flow<Resource<SelectedMovie>>{
        return flow {
            emit(Resource.loading(null))
            val result = movieRepository.getMovie(imdbId)
            result.data?.let {
                emit(Resource.success(it.toSelectedMovie()))
            } ?: kotlin.run {
                emit(Resource.error(result.message ?: "", null))
            }
        }.flowOn(Dispatchers.IO)
    }

}