package com.example.campusevents.presentation.favourite

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.campusevents.data.EventsRepository
import com.example.campusevents.data.FireStoreModelResponse
import com.example.campusevents.data.ResultState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavouritesViewModel @Inject constructor(
    private val eventsRepository: EventsRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<List<FireStoreModelResponse>>(emptyList())
    val uiState = _uiState.asStateFlow()

    init {
        getFavourites()
    }

    fun getFavourites() = viewModelScope.launch {
        eventsRepository.getFavourites().collect {
            when (it) {
                is ResultState.Success -> {
                    Log.d("VM", "Success: result : ${it.result}")
                    _uiState.value = it.result
                }

                is ResultState.Loading -> {
                    Log.d("DB", "loading")
                }

                is ResultState.Failure -> {
                    Log.d("DB", "Error ${it.exception}")
                }
            }
        }
    }

    fun removeFavourite(eventId: String) = viewModelScope.launch {
        eventsRepository.removeFavourite(eventId).collect {
            when (it) {
                is ResultState.Success -> {
                    Log.d("VM", it.result)
                }

                is ResultState.Loading -> {
                    Log.d("DB", "loading")
                }

                is ResultState.Failure -> {
                    Log.d("DB", "Error ${it.exception}")
                }
            }
        }
    }
}


