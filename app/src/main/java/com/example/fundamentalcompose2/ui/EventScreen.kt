package com.example.fundamentalcompose2.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Shapes
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.fundamentalcompose2.ResultState
import com.example.fundamentalcompose2.component.BottomBar
import com.example.fundamentalcompose2.component.SearchBar
import com.example.fundamentalcompose2.data.response.ListEventsItem
import com.example.fundamentalcompose2.viewmodel.AllEventViewModel


@Composable
fun AllActiveMainScreen(modifier: Modifier = Modifier, navController: NavController) {
    Scaffold(
        bottomBar = {
            BottomBar(navController = navController)
        },
        topBar = {
            SearchBar()
        }
    ) {
        Column(
            modifier = modifier.padding(it)
        ) {
            EventScreen()

        }
    }

}

@Composable
fun EventScreen(viewModel: AllEventViewModel = hiltViewModel(), modifier: Modifier = Modifier) {
    val resultState by viewModel.allEvents.observeAsState(ResultState.Loading)

    LaunchedEffect(key1 = Unit) {
        viewModel.fetchAllEvents()
    }

    when (resultState) {
        is ResultState.Loading -> {
            Column (
                horizontalAlignment =  Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = modifier
                    .fillMaxSize()
            ){
                CircularProgressIndicator(
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }

        is ResultState.Success -> {
            val events = (resultState as ResultState.Success).data
            LazyColumn {
                items(events.listEvents) { event ->
                    EventAllItem(event)
                }
            }
        }

        is ResultState.Error -> {
            val error = (resultState as ResultState.Error).error
            Text(text = error)
        }
    }
}
@Composable
fun EventAllItem(data: ListEventsItem, modifier: Modifier = Modifier) {
    ElevatedCard(
        elevation = CardDefaults.cardElevation(),
        shape = Shapes().medium,
        colors = CardDefaults.cardColors(),
        modifier = modifier
            .padding(8.dp)
            .shadow(4.dp)
    ) {
        Column {
            Image(
                painter = rememberAsyncImagePainter(model = data.mediaCover),
                contentDescription = null,
                contentScale = ContentScale.Crop, // Crop to fill the space
                modifier = Modifier
                    .fillMaxWidth() // Make the image fill the card width
                    .aspectRatio(16f / 9f) // Adjust the aspect ratio as needed
            )
            Text(
                text = data.name,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(8.dp)
            )
        }
    }
}

