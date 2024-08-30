//package com.example.fundamentalcompose2.services
//
//import android.Manifest
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.LaunchedEffect
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.livedata.observeAsState
//import androidx.compose.ui.platform.LocalContext
//import androidx.hilt.navigation.compose.hiltViewModel
//import com.example.fundamentalcompose2.ResultState
//import com.example.fundamentalcompose2.viewmodel.NotificationViewModel
//import com.google.accompanist.permissions.rememberPermissionState
//
//@Composable
//fun NotificationScreen(viewModel: NotificationViewModel = hiltViewModel(), content: @Composable () -> Unit) {
//    val resultState by viewModel.notification.observeAsState(ResultState.Loading)
//    val context = LocalContext.current
//
//    // Create a permission state instance
//    val postNotificationPermission = rememberPermissionState(permission = Manifest.permission.POST_NOTIFICATIONS)
//
//    LaunchedEffect(Unit) {
//        // Request permission if not granted
//        if (!postNotificationPermission.hasPermission) {
//            postNotificationPermission.launchPermissionRequest()
//        }
//        viewModel.fetchNotification()
//    }
//
//    when (resultState) {
//        is ResultState.Loading -> {
//            // Show loading indicator or other UI components
//        }
//        is ResultState.Success -> {
//            val events = (resultState as ResultState.Success).data.listEvents
//            events.forEach { event ->
//                // Pass event data to EventNotificationService
//                EventNotificationService(
//                    context = context,
//                    contentTitle = event.name,  // Ensure event.name is the correct property
//                    contentText = event.summary,
//                    imageUrl = event.mediaCover
//                ).showExpandableNotification()  // Ensure showExpandableNotification() method exists
//            }
//        }
//        is ResultState.Error -> {
//            // Handle errors if needed
//        }
//    }
//}
