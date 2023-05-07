package com.example.campusevents.presentation.add_event

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime


data class AddEventUiState(
    val title : String = "",
    val description: String = "",
    val date: LocalDate,
    val time: LocalTime,
    val dateTIme: LocalDateTime
)