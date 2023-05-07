package com.example.campusevents.presentation.event_list

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
class EventsListViewModel @Inject constructor(
    private val eventsRepository: EventsRepository
): ViewModel() {
    private val _uiState = MutableStateFlow<List<FireStoreModelResponse>>(emptyList())
    val uiState = _uiState.asStateFlow()

    private val _favourites = MutableStateFlow<List<FireStoreModelResponse>>(emptyList())
    val favourites = _favourites.asStateFlow()

    init {
        getAllEvents()
        getFavourites()
    }


    fun getAllEvents() = viewModelScope.launch{

        eventsRepository.getTodaysEvents().collect {
            when(it) {
                is ResultState.Success -> {
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

    fun addFavourite(eventId: String) = viewModelScope.launch {
        eventsRepository.addToFavourites(eventId).collect {
            when(it) {
                is ResultState.Success -> {
                    Log.d("VM", "data: ${it.result}")
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

     fun getFavourites() = viewModelScope.launch {
        eventsRepository.getFavourites().collect {
            when(it) {
                is ResultState.Success -> {
                    _favourites.value = it.result
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