package com.example.fundamentalcompose2.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.example.fundamentalcompose2.ResultState
import com.example.fundamentalcompose2.data.response.ListEventsItem
import com.example.fundamentalcompose2.viewmodel.MainViewModel

@Composable
fun ActiveEventScreen(viewModel: MainViewModel = hiltViewModel(), modifier: Modifier = Modifier) {
    val resultState by viewModel.events.observeAsState(ResultState.Loading)

    LaunchedEffect(key1 = Unit) {
        viewModel.fetchEvents()
    }

    when (resultState) {
        is ResultState.Loading -> {
            Column (
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = modifier.fillMaxSize()
            ){
                CircularProgressIndicator()
            }
        }

        is ResultState.Success -> {
            val events = (resultState as ResultState.Success).data
            Scaffold(modifier = modifier) {
                Column(
                    modifier = modifier
                        .padding(it),
                ) {
                    TitleEvent(title = "Upcoming Events")
                    EventItem(data = events.listEvents[0])
//                    LazyRow(
//                        horizontalArrangement = Arrangement.Center,
//                        verticalAlignment = Alignment.Top
//                    ) {
//                        items(events.listEvents) { event ->
//                            EventItem(event)
//                        }
//                    }
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
fun TitleEvent(title: String) {
    Text(
        text = title,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp,
        modifier = Modifier.padding(start=16.dp, bottom = 16.dp)
    )
}

@Composable
fun EventItem(data: ListEventsItem, modifier: Modifier = Modifier) {
    ElevatedCard(
        elevation = CardDefaults.cardElevation(),
        shape = Shapes().medium,
        colors = CardDefaults.cardColors(),
        modifier = modifier
            .padding(start = 8.dp, end = 8.dp)
            .shadow(8.dp)
    ) {
        Column(
            modifier = modifier
                .padding(8.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Top
        ) {

            Image(
                painter = rememberAsyncImagePainter(model = data.mediaCover),
                contentDescription = null,
                modifier = modifier
                    .size(width = 400.dp, height = 200.dp)
            )
            Text(
                text = data.name, fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                modifier = modifier
                    .padding(start = 16.dp, top = 8.dp),
            )
            Text(
                text = data.summary,
                fontSize = 12.sp,
                modifier = modifier
                    .padding(start = 16.dp, top = 8.dp),
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun EventScreenPreview() {
    ActiveEventScreen()
}