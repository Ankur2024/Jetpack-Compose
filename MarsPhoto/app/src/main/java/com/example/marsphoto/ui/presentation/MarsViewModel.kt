package com.example.marsphoto.ui.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.marsphoto.MarsPhotosApplication
import com.example.marsphoto.data.MarsPhotosRepository
import com.example.marsphoto.network.MarsPhoto
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException


sealed interface MarsUiState {
    data class Success(val photos: List<MarsPhoto>) : MarsUiState
    object Error : MarsUiState
    object Loading : MarsUiState
}

class MarsViewModel(
    private val marsPhotosRepository: MarsPhotosRepository
) : ViewModel() {
    var marsUiState: MarsUiState by mutableStateOf(MarsUiState.Loading)
        private set

    init {
        getMarsPhotos()
    }

    fun getMarsPhotos() {
        viewModelScope.launch {
            try {
                val result = marsPhotosRepository.getMarsPhotos()[0]
                marsUiState = MarsUiState.Success(marsPhotosRepository.getMarsPhotos())
            } catch (e: IOException) {
                marsUiState = MarsUiState.Error
            } catch (e: HttpException) {
                marsUiState = MarsUiState.Error
            }
        }
    }
    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as MarsPhotosApplication)
                val marsPhotosRepository = application.container.marsPhotosRepository
                MarsViewModel(marsPhotosRepository = marsPhotosRepository)
            }
        }
    }
}
