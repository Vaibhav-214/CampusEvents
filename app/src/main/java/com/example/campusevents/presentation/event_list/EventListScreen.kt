package com.example.campusevents.presentation.event_list

import android.os.Build
import android.util.Log
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
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.campusevents.R
import com.example.campusevents.data.FireStoreModelResponse
import com.example.campusevents.navigation.Screens
import java.time.ZoneId

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventsListScreen(
    navController: NavHostController,
    viewModel: EventsListViewModel
) {

    val eventsList by remember {
        viewModel.uiState
    }.collectAsState()

    val favouritesList by remember {
        viewModel.favourites
    }.collectAsState()




    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Events List") },
                actions = {

                    IconButton(onClick = { navController.navigate(Screens.FavouritesScreen.route) }) {
                        Icon(imageVector = Icons.Filled.Favorite, contentDescription = null)
                    }
                }

            )
        },
    ) {pad->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(pad)
                .padding(horizontal = 10.dp),
            verticalArrangement = Arrangement.spacedBy(5.dp)
            ) {
            items(eventsList) {model->
                EventBlock(
                    event = model,
                    onClick = {
                        navController.navigate(Screens.EventDetailScreen
                            .createRoute(eventId = it))},
                    addFavourite = {viewModel.addFavourite(it)},
                    favouriteList = favouritesList
                    )
            }
        }

    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun EventBlock(
    event: FireStoreModelResponse,
    onClick: (String) -> Unit,
    addFavourite: (String) -> Unit,
    favouriteList: List<FireStoreModelResponse>
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            //.height(65.dp)
            .padding(vertical = 10.dp)
            .clickable { onClick(event.id ?: "null event id") },
        shape = RoundedCornerShape(10.dp),
        shadowElevation = 10.dp,
        color = colorResource(id = R.color.new_purple),
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
            var bool by remember {
                mutableStateOf(false)
            }
            IconButton(onClick = {
                addFavourite(event.id ?: "null")
            }) {


                for (fav in favouriteList) {
                    if (fav.item!!.title == event.item!!.title) {
                        bool = true
                        break
                    }
                }
                val imageVector = if (bool) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder
                Icon(
                    imageVector = imageVector,
                    contentDescription = null,
                    tint = Color.White
                    )
            }
        }


    }
}