package com.example.fundamentalcompose2.ui

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.example.fundamentalcompose2.R
import com.example.fundamentalcompose2.ResultState
import com.example.fundamentalcompose2.data.response.ResponseDetail
import com.example.fundamentalcompose2.viewmodel.DetailViewModel

@Composable
fun DetailScreen(
    id: Int,
    viewModel: DetailViewModel = hiltViewModel(),
    navController: NavController
) {

    val resultState by viewModel.isDetail.observeAsState(ResultState.Loading)


    LaunchedEffect(key1 = Unit) {
        viewModel.getEventDetail(id)
    }

    when (resultState) {
        is ResultState.Loading -> {
            // Tampilkan indikator loading jika diperlukan
        }

        is ResultState.Success -> {
            val data = (resultState as ResultState.Success).data
            DetailItemScreen(data, navController = navController)
        }

        is ResultState.Error -> {
            val error = (resultState as ResultState.Error).error
            // Tampilkan pesan kesalahan jika diperlukan
        }
    }
}

@Composable
fun DetailItemScreen(data: ResponseDetail, modifier: Modifier = Modifier, navController: NavController) {
    val context = LocalContext.current
    Scaffold {
        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp),
            modifier = modifier
                .fillMaxSize()
                .padding(it)
                .verticalScroll(rememberScrollState())
        ) {
            Box(
                contentAlignment = Alignment.TopStart,
                modifier = modifier
                    .fillMaxWidth()
                    .size(height = 300.dp, width = 300.dp)
                    .clip(RoundedCornerShape(10.dp))
            ) {
                Image(
                    painter = rememberAsyncImagePainter(model = data.event.mediaCover),
                    contentDescription = null,
                    modifier
                        .fillMaxWidth()
                        .fillMaxHeight(), contentScale = ContentScale.FillBounds
                )
                IconButton(
                    onClick = {
                        navController.popBackStack()
                    },
                    modifier = modifier
                        .padding(top = 28.dp, start = 18.dp)
                        .clip(RoundedCornerShape(20.dp))
                        .size(40.dp)
                        .background(MaterialTheme.colorScheme.primaryContainer)
                ) {
                    Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = null)
                }
            }
            Column(
                verticalArrangement = Arrangement.spacedBy(10.dp),
                modifier = modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            ) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = modifier.fillMaxWidth()
                ) {
                    Text(
                        text = data.event.name,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        modifier = modifier.weight(1f)

                    )
                    IconButton(onClick = {
                    }) {
                        Icon(imageVector = Icons.Outlined.FavoriteBorder, contentDescription = null)
                    }
                }
                Row(
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.logodicoding),
                        contentDescription = null,
                        modifier = modifier.size(60.dp)
                    )
                    Text(
                        text = data.event.ownerName,
                        fontWeight = FontWeight.Light,
                        fontSize = 20.sp
                    )
                    VerticalDivider(modifier.size(20.dp))
                    Text(text = data.event.cityName, fontWeight = FontWeight.Bold)
                }
                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = modifier
                        .fillMaxWidth()
                ) {
                    ElevatedCard(
                        elevation = CardDefaults.cardElevation(
                            defaultElevation = 1.dp,
                        ),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.primaryContainer,
                            contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                        ),
                        shape = CardDefaults.elevatedShape,
                        modifier = modifier
                            .shadow(2.dp, RoundedCornerShape(20.dp))
                            .weight(1.3f)
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(20.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = modifier.padding(18.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.Person,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.secondary,
                                modifier = modifier.size(40.dp)
                            )
                            Column {
                                Text(
                                    text = "Enrolled",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 20.sp
                                )
                                Row(
                                    horizontalArrangement = Arrangement.spacedBy(5.dp)
                                ) {
                                    Text(
                                        text = data.event.registrants.toString(),
                                        fontWeight = FontWeight.Light,
                                        fontSize = 18.sp
                                    )
                                    Text(
                                        text = "People",
                                        fontWeight = FontWeight.Light,
                                        fontSize = 16.sp
                                    )
                                }
                            }
                        }
                    }
                    ElevatedCard(
                        elevation = CardDefaults.cardElevation(
                            defaultElevation = 1.dp,
                        ),
                        shape = CardDefaults.elevatedShape,
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.primaryContainer,
                            contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                        ),
                        modifier = modifier
                            .shadow(2.dp, RoundedCornerShape(20.dp))
                            .weight(1.3f)
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(20.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = modifier.padding(18.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.Person,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.secondary,
                                modifier = modifier.size(40.dp)
                            )
                            Column {
                                Text(
                                    text = "Available",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 20.sp
                                )
                                Row(
                                    horizontalArrangement = Arrangement.spacedBy(5.dp)
                                ) {
                                    Text(
                                        text = data.event.quota.toString(),
                                        fontWeight = FontWeight.Light,
                                        fontSize = 17.sp
                                    )
                                    Text(
                                        text = "People",
                                        fontWeight = FontWeight.Light,
                                        fontSize = 15.sp
                                    )
                                }
                            }
                        }
                    }
                }
                Text(
                    modifier = modifier.padding(top = 8.dp, bottom = 8.dp),
                    text = data.event.summary,
                    fontSize = 18.sp,
                )
                Row(
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Company :",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                    Text(text = data.event.ownerName, fontSize = 18.sp)
                }
                Row(
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Diselenggarakan :",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                    Text(text = data.event.cityName, fontSize = 18.sp)
                }
                Row(
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = modifier.fillMaxWidth()
                ) {
                    Text(text = "Start Date :", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                    Text(text = data.event.beginTime, fontSize = 18.sp)
                }
                Row(
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = modifier.fillMaxWidth()
                ) {
                    Text(text = "End Date", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                    Text(text = data.event.endTime, fontSize = 18.sp)
                }
                Column(
                    modifier = modifier.fillMaxWidth()
                ) {
                    Button(
                        onClick = {
                            val link = data.event.link
                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(link))
                            context.startActivity(intent)
                        },
                        modifier = modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Text(
                            text = "Register", fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ShowLink(link: String) {
    val context = LocalContext.current
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(link))
    context.startActivity(intent)
}
