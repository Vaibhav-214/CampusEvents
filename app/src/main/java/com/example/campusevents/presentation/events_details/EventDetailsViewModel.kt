package com.example.campusevents.presentation.events_details

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
class EventDetailsViewModel @Inject constructor(
    private val eventsRepository: EventsRepository
): ViewModel() {


    private val _uiState = MutableStateFlow<FireStoreModelResponse.Event?>(null)
    val uiState = _uiState.asStateFlow()


    fun getEvent(id: String) = viewModelScope.launch {
        eventsRepository.getEvent(id).collect {
            when(it) {
                is ResultState.Success -> {
                    _uiState.value = it.result
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

    fun deleteEvent(id: String) = viewModelScope.launch {
        eventsRepository.deleteEvent(id).collect {
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

}