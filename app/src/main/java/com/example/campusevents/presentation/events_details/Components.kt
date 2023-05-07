package com.example.campusevents.presentation.events_details

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.example.campusevents.R


@Composable
fun TextComponent(
    text: String,
    style: TextStyle,
    height: Int,
    alignment: Alignment
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(height.dp),
        shape = RoundedCornerShape(10.dp),
        color = colorResource(id = R.color.purple),
        shadowElevation = 10.dp
    ) {

        Box (){
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp, vertical = 5.dp)
                    .align(alignment = alignment),

                text = text,
                style = style
            )
        }
    }
}