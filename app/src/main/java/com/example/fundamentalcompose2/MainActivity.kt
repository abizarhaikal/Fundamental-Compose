package com.example.fundamentalcompose2

import EventNotificationService
import android.Manifest
import android.os.Build
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.fundamentalcompose2.component.BottomBar
import com.example.fundamentalcompose2.component.TopAppBar
import com.example.fundamentalcompose2.model.AppNavHost
import com.example.fundamentalcompose2.ui.ActiveEventScreen
import com.example.fundamentalcompose2.ui.EventPast
import com.example.fundamentalcompose2.ui.theme.FundamentalCompose2Theme
import com.example.fundamentalcompose2.viewmodel.NotificationViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private lateinit var notificationViewModel: NotificationViewModel

    @OptIn(ExperimentalPermissionsApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        notificationViewModel = ViewModelProvider(this)[NotificationViewModel::class.java]

        setContent {
            FundamentalCompose2Theme {
                // Request notification permission if necessary
                val notificationPermissionState =
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        rememberPermissionState(permission = Manifest.permission.POST_NOTIFICATIONS)
                    } else {
                        null
                    }

                LaunchedEffect(key1 = Unit) {
                    notificationViewModel.fetchNotification()
                }
                LaunchedEffect(key1 = notificationPermissionState) {
                    if (notificationPermissionState != null && !notificationPermissionState.status.isGranted) {
                        notificationPermissionState.launchPermissionRequest()
                    }
                }

                // Observe and handle notifications
                notificationViewModel.notification.observe(this@MainActivity) { result ->
                    when (result) {
                        is ResultState.Loading -> {
                            // Handle loading state if needed
                        }

                        is ResultState.Success -> {
                            val events = result.data.listEvents.first()
                            if (events != null) {
                                val eventNotificationService = EventNotificationService(
                                    context = this@MainActivity,
                                    contentTitle = events.name,
                                    contentText = events.summary,
                                    imageUrl = events.imageLogo,
                                    eventId = events.id
                                )
                                lifecycleScope.launch {
                                    eventNotificationService.showExpandableNotification()
                                }
                            }
                        }

                        is ResultState.Error -> {
                            Log.e("MainActivity", "Error: ${result.error}")
                        }
                    }
                }

                enableEdgeToEdge()

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
            EventPast()
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
