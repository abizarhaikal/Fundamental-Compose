package com.example.fundamentalcompose2.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarColors
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.example.fundamentalcompose2.ResultState
import com.example.fundamentalcompose2.data.response.ResponseEvents
import com.example.fundamentalcompose2.ui.EventAllItem
import com.example.fundamentalcompose2.viewmodel.SearchViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(viewModel: SearchViewModel = hiltViewModel()) {
    val lifecycleOwner = LocalLifecycleOwner.current
    var searchResult by remember {
        mutableStateOf<ResultState<ResponseEvents>?>(null)
    }

    viewModel.searchEvents.observe(lifecycleOwner) { result ->
        searchResult = result
    }

    val searchText by viewModel.searchText.collectAsState()
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.padding(top = 32.dp, start = 32.dp, end = 32.dp, bottom = 16.dp)
    ) {
        SearchBar(
            colors = SearchBarDefaults.colors(
                containerColor = MaterialTheme.colorScheme.surfaceContainer,
                dividerColor = MaterialTheme.colorScheme.surfaceContainer,
            ),
            leadingIcon = { Icon(imageVector = Icons.Default.Person, contentDescription = null) },
            trailingIcon = { Icon(imageVector = Icons.Default.Search, contentDescription = null) },
            placeholder = { Text(text = "Search...") },
            query = searchText,
            onQueryChange = { viewModel.onSearchTextChange(it) },
            onSearch = { viewModel.searchEvents() },
            shadowElevation = 8.dp,
            shape = SearchBarDefaults.dockedShape,
            active = searchText.isNotEmpty(),
            onActiveChange = { isActive ->
                if (!isActive) {
                    viewModel.onSearchTextChange("")
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Transparent)
        ) {
            when (val result = searchResult) {
                is ResultState.Loading -> {
                    Column (
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxSize()
                    ){
                        CircularProgressIndicator()
                    }
                }

                is ResultState.Success -> {
                    if (result.data.listEvents.isEmpty()) {
                        Column (
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center,
                            modifier = Modifier.fillMaxSize()
                        ){
                            Text(text = "Not Found", fontWeight = FontWeight.Bold)
                        }
                        return@SearchBar
                    }
                    val events = (result).data
                    val navController = rememberNavController()
                    Scaffold(
                        bottomBar = { BottomBar(navController = navController) },
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(it)
                        ) {
                            LazyColumn(
                                modifier = Modifier
                                    .background(Color.Transparent)
                            ) {
                                items(events.listEvents) { event ->
                                    EventAllItem(event)
                                }
                            }

                        }
                    }
                }

                is ResultState.Error -> {
                    Text(text = result.error)
                }

                else -> {
                    Text(text = "Not Found")
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SearchBarPreview() {
    SearchBar()
}