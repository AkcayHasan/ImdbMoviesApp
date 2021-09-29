package com.example.imdbfilmapp.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.imdbfilmapp.features.movies.domain.entities.Movie
import com.example.imdbfilmapp.features.movies.domain.entities.Movies
import com.example.imdbfilmapp.features.movies.domain.usecases.HomeUseCase
import com.example.imdbfilmapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeUseCase: HomeUseCase
) : ViewModel() {

    private val movie =
        MutableStateFlow<Resource<Movies>>(Resource.success(null))
    val listMovie: StateFlow<Resource<Movies>>
        get() = movie

    suspend fun getMovies(searchText: String, pageNum: Int) {
        movie.value = Resource.loading(null)
        viewModelScope.launch {
            try {
                homeUseCase.getAllMovies(searchText, pageNum).collect {
                    movie.value = it
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

}