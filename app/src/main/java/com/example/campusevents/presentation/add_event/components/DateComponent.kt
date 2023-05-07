package com.example.campusevents.presentation.add_event.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.campusevents.ui.theme.CampusEventsTheme
import com.maxkeppeker.sheets.core.models.base.UseCaseState
import com.maxkeppeler.sheets.calendar.CalendarDialog
import com.maxkeppeler.sheets.calendar.models.CalendarConfig
import com.maxkeppeler.sheets.calendar.models.CalendarSelection
import java.time.LocalDate


@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerField(
    onDateChange: (LocalDate) -> Unit,
    calState: UseCaseState,
    date: LocalDate?,
    ) {

    CalendarDialog(
        state = calState,
        selection = CalendarSelection.Date(
            selectedDate = date
        ) {newDate ->
            onDateChange(newDate)
        },
        config = CalendarConfig(
            monthSelection = true,
            yearSelection = true
        ),
    )

    val dateString = buildString {
        date?.let {
            append(it.dayOfMonth)
            append(" - ")
            append(it.month)
            append(" - ")
            append(it.year)
        }

    }
    Surface (
        modifier = Modifier.fillMaxWidth().height(55.dp),
        border = BorderStroke(
            width = 1.dp,
            color = Color.Gray
        ),
        shape = RoundedCornerShape(4.dp)
    ){
        Row (
            modifier = Modifier.fillMaxWidth().clickable { calState.show() },
            verticalAlignment = Alignment.CenterVertically
            ) {
            
            Text(
                text = dateString,
                modifier = Modifier
                    .fillMaxWidth(0.85f).padding(start = 10.dp))
            
            IconButton(onClick = {  }) {
                Icon(
                    imageVector = Icons.Filled.DateRange,
                    contentDescription = null,
                    modifier = Modifier.size(30.dp)
                    )
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showSystemUi = true)
@Composable
fun DatePickerFieldPreview() {
    CampusEventsTheme {

    }
}