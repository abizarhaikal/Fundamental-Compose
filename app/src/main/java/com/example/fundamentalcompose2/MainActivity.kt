package com.example.fundamentalcompose2

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.fundamentalcompose2.component.BottomBar
import com.example.fundamentalcompose2.component.TopAppBar
import com.example.fundamentalcompose2.data.remote.ApiService
import com.example.fundamentalcompose2.model.AppNavHost
import com.example.fundamentalcompose2.ui.ActiveEventScreen
import com.example.fundamentalcompose2.ui.theme.FundamentalCompose2Theme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var apiService: ApiService
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        GlobalScope.launch {
            val response = apiService.getEvents()
            Log.d("MainActivity", "onCreate: ${response.message()}")
        }
        enableEdgeToEdge()
        setContent {
            FundamentalCompose2Theme {
                val navController = rememberNavController()
                Surface {
                    AppNavHost(navController = navController)
                }
            }
        }
    }
}


@Composable
fun ActiveMainScreen(modifier: Modifier = Modifier, navController: NavController) {
    val context = LocalContext.current
    Scaffold(
        topBar = {
            TopAppBar(
                title = "Dicoding Event",
                context = context,
                icon = Icons.Default.Search
            )
        },
        bottomBar = {
            BottomBar(navController = navController)
        }
    ) { innerPadding ->
        Column(
            modifier = modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            ActiveEventScreen()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    FundamentalCompose2Theme {
        val navController = rememberNavController()
        ActiveMainScreen(navController = navController)
    }
}