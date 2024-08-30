package com.example.fundamentalcompose2.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
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
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = modifier.fillMaxSize()
            ) {
                CircularProgressIndicator()
            }
        }

        is ResultState.Success -> {
            val events = (resultState as ResultState.Success).data
            Scaffold(modifier = modifier) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = modifier
                        .padding(it)
                        .verticalScroll(rememberScrollState()),
                ) {
                    TitleEvent(title = "Upcoming Events")
//                    EventItem(data = events.listEvents[0])
                    LazyRow(
                        horizontalArrangement = Arrangement.Center,
                    ) {
                        items(events.listEvents) { event ->
                            EventItem(event)
                        }
                    }
                    TitleEvent(title = "Past Events")
                    EventPast()
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
        modifier = Modifier.padding(start = 16.dp, bottom = 16.dp)
    )
}

@Composable
fun EventItem(data: ListEventsItem, modifier: Modifier = Modifier) {
    ElevatedCard(
        elevation = CardDefaults.cardElevation(),
        shape = Shapes().medium,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.onPrimary,
            contentColor = MaterialTheme.colorScheme.secondary
        ),
        modifier = modifier
            .padding(8.dp)
            .shadow(8.dp)
            .fillMaxWidth() // Memastikan card mengisi lebar layar
    ) {
        Column(
            modifier = Modifier.padding(8.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Top
        ) {
            Image(
                painter = rememberAsyncImagePainter(model = data.mediaCover),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .widthIn(max = 400.dp)
                    .heightIn(max = 200.dp) // Membatasi tinggi gambar
                    .clip(Shapes().medium), // Menambahkan shape pada gambar
                contentScale = ContentScale.Crop // Mengatur gambar agar crop sesuai dengan ukuran kontainer
            )
            Spacer(modifier = Modifier.height(8.dp))
            Column (
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Center,
                modifier = modifier
                    .fillMaxWidth()
                    .widthIn(max = 400.dp)
            ) {

                Text(
                    text = data.name,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    overflow = TextOverflow.Ellipsis, // Tambahkan ellipsis jika teks terlalu panjang
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp), // Menambahkan padding agar teks tidak terlalu dekat dengan batas
                    maxLines = 1 // Membatasi teks pada satu baris
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = data.summary,
                    fontSize = 12.sp,
                    overflow = TextOverflow.Ellipsis, // Tambahkan ellipsis jika teks terlalu panjang
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp), // Menambahkan padding agar teks tidak terlalu dekat dengan batas
                    maxLines = 2 // Membatasi teks pada dua baris
                )
            }
        }
    }
}



@Preview(showBackground = true)
@Composable
fun EventScreenPreview() {
    ActiveEventScreen()
}