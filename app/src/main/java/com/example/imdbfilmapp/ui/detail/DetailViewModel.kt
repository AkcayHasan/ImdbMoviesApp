package com.example.imdbfilmapp.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.imdbfilmapp.features.movies.domain.entities.SelectedMovie
import com.example.imdbfilmapp.features.movies.domain.usecases.DetailUseCase
import com.example.imdbfilmapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val detailUseCase: DetailUseCase
): ViewModel() {

    private val selectedMovie =
        MutableStateFlow<Resource<SelectedMovie>>(Resource.success(null))
    val resultMovie: StateFlow<Resource<SelectedMovie>>
        get() = selectedMovie

    suspend fun getSelectedMovie(imdbId: String) {
        selectedMovie.value = Resource.loading(null)
        viewModelScope.launch {
            try {
                detailUseCase.getSelectedMovie(imdbId).collect {
                    selectedMovie.value = it
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

}