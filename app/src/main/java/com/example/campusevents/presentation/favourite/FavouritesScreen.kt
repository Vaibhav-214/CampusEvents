package com.example.campusevents.presentation.favourite

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import com.example.campusevents.R
import com.example.campusevents.data.FireStoreModelResponse
import com.example.campusevents.navigation.Screens
import com.example.campusevents.presentation.event_list.EventBlock
import java.time.ZoneId

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavouritesScreen(
    viewModel: FavouritesViewModel,
    navController: NavHostController
) {

    val favouritesList by remember {
        viewModel.uiState
    }.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Favourites") },

                )
        },
    ) { pad ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(pad)
                .padding(horizontal = 10.dp),
            verticalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            items(favouritesList) { model ->
                FavouriteItemBlock(
                    event = model,
                    navigate = {
                        navController.navigate(
                            Screens.EventDetailScreen
                                .createRoute(eventId = it)
                        )
                    },
                    removeFavourite = { viewModel.removeFavourite(it) }
                )
            }
        }
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun FavouriteItemBlock(
    event: FireStoreModelResponse,
    navigate: (String) -> Unit,
    removeFavourite: (String) -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            //.height(65.dp)
            .padding(vertical = 10.dp)
            .clickable { navigate(event.id ?: "null event id") },
        shape = RoundedCornerShape(10.dp),
        shadowElevation = 10.dp,
        color = colorResource(id = R.color.purple),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = event.item?.title ?: "null",
                    modifier = Modifier.padding(10.dp),
                    color = Color.White,
                    fontSize = 30.sp
                )

                Text(
                    text = event.item?.timestamp!!.toDate().toInstant().atZone(ZoneId.systemDefault())
                        .toLocalDate().toString(),
                    modifier = Modifier.padding(10.dp),
                    color = Color.White,
                    fontSize = 30.sp
                )
            }
            IconButton(onClick = { removeFavourite(event.id ?: "null") }) {
                Icon(imageVector = Icons.Filled.Clear, contentDescription = null)
            }
        }
    }
}