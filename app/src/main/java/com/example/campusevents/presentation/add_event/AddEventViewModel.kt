package com.example.campusevents.presentation.add_event

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.campusevents.data.EventsRepository
import com.example.campusevents.data.FireStoreModelResponse
import java.sql.Timestamp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.util.Date
import javax.inject.Inject


@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class AddEventViewModel @Inject constructor(
    private val repository: EventsRepository
): ViewModel() {


    private val _uiState = MutableStateFlow<AddEventUiState?>(
        AddEventUiState("","",
            LocalDate.now(),
            LocalTime.now(),
            LocalDateTime.now()
            )
    )
    val uiState = _uiState.asStateFlow()

    fun onDateChange(date: LocalDate) {
        _uiState.value = _uiState.value?.copy(
            date = date,
            dateTIme = LocalDateTime.of(date, _uiState.value!!.time)
        )
    }

    fun onTimeChange(time: LocalTime) {
        _uiState.value = _uiState.value?.copy(
            time = time,
            dateTIme = LocalDateTime.of(_uiState.value!!.date, time)
        )
    }

    fun onTitleChange(newValue: String) {
        _uiState.value = _uiState.value?.copy(
            title = newValue
        )
    }

    fun onDescriptionChange(newValue: String) {
        _uiState.value = _uiState.value?.copy(
            description = newValue
        )
    }


    fun addEvent() = viewModelScope.launch {
        val javaDateTime = _uiState.value!!.dateTIme
        val withOffset = javaDateTime.atZone(ZoneId.systemDefault())
        val dateobject = Date.from(withOffset.toInstant())

        repository.addEvent(
            FireStoreModelResponse.Event(
                title = _uiState.value!!.title,
                description = _uiState.value!!.description,
                //timestamp = Timestamp(_uiState.value!!.dateTIme.toEpochSecond(ZoneOffset.UTC))
                timestamp = com.google.firebase.Timestamp(dateobject)
                // above Timestamp type comes from firebase
            )
        ).collect {
            Log.d("FB", it.toString())
        }
    }
}