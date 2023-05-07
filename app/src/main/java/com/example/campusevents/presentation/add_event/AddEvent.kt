package com.example.campusevents.presentation.add_event

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.campusevents.presentation.add_event.components.DatePickerField
import com.example.campusevents.presentation.add_event.components.TimePicker
import com.example.campusevents.ui.theme.CampusEventsTheme
import com.maxkeppeker.sheets.core.models.base.rememberUseCaseState

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEvent(
    viewModel: AddEventViewModel = hiltViewModel()
) {

    val calendarState = rememberUseCaseState()

    val timeState = rememberUseCaseState()

    val uiState by remember {
        viewModel.uiState
    }.collectAsState()




    Column (
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
    ){

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = uiState?.title ?: "empty",
            onValueChange = {viewModel.onTitleChange(it)},
            label = { Text(text = "Title")}
            )

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .height(140.dp) ,
            value = uiState?.description ?: "empty",
            onValueChange = {viewModel.onDescriptionChange(it)},
            label = { Text(text = "Description")}
        )

        Spacer(modifier = Modifier.height(18.dp))

        DatePickerField(
           // date = date,
            calState = calendarState,
            date = uiState?.date,
            onDateChange = {viewModel.onDateChange(it)},
        )

        Spacer(modifier = Modifier.height(18.dp))

        TimePicker(
            timeState = timeState,
            time = uiState?.time,
            onTimeChange = {viewModel.onTimeChange(it)},
        )

        Spacer(modifier = Modifier.height(18.dp))

        Button(
            modifier = Modifier.width(150.dp).align(Alignment.CenterHorizontally),
            onClick = { viewModel.addEvent() }) {
            Text(text = "Save")
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showSystemUi = true)
@Composable
fun AddEventPreview() {
    CampusEventsTheme {
        AddEvent()
    }
}