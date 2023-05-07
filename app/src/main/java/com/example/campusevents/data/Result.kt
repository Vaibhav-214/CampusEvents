package com.example.campusevents.data

sealed class ResultState<out R> {
    data class Success<out R>(
        val result: R
    ) : ResultState<R>()

    data class Failure(
        val exception: Exception
    ) : ResultState<Nothing>()

    object Loading : ResultState<Nothing>()
}

