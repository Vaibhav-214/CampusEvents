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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.campusevents.R
import com.maxkeppeker.sheets.core.models.base.UseCaseState
import com.maxkeppeler.sheets.clock.ClockDialog
import com.maxkeppeler.sheets.clock.models.ClockConfig
import com.maxkeppeler.sheets.clock.models.ClockSelection
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimePicker(
    timeState: UseCaseState,
    time: LocalTime?,
    onTimeChange: (LocalTime) -> Unit,
) {


    ClockDialog(
        state = timeState,
        selection = ClockSelection.HoursMinutesSeconds { hours, minutes, seconds ->
             onTimeChange(LocalTime.of(hours, minutes, seconds))
        },
        config = ClockConfig(
            is24HourFormat = false
        )
    )

    Surface (
        modifier = Modifier
            .fillMaxWidth()
            .height(55.dp),
        border = BorderStroke(
            width = 1.dp,
            color = Color.Gray
        ),
        shape = RoundedCornerShape(4.dp)
    ){
        Row (
            modifier = Modifier.fillMaxWidth().clickable { timeState.show() },
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                text = time?.format(DateTimeFormatter.ofPattern("hh:mm a")) ?: "",
                modifier = Modifier
                    .fillMaxWidth(0.85f)
                    .padding(start = 10.dp))

            IconButton(onClick = {  }) {
                Icon(
                    painter = painterResource(id = R.drawable.time),
                    contentDescription = null,
                    modifier = Modifier.size(30.dp)
                    )
            }
        }
    }

}