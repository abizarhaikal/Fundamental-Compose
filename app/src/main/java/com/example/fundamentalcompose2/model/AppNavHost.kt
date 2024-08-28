package com.example.fundamentalcompose2.model

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.fundamentalcompose2.ActiveMainScreen
import com.example.fundamentalcompose2.ui.ActiveEventScreen
import com.example.fundamentalcompose2.ui.AllActiveMainScreen
import com.example.fundamentalcompose2.ui.EventScreen

@Composable
fun AppNavHost(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(
        navController = navController,
        startDestination = Routes.EventScreen.route,
        modifier = modifier
    ) {
        composable(Routes.ActiveEventScreen.route) {
            ActiveMainScreen(navController = navController)
        }
        composable(Routes.EventScreen.route) {
            AllActiveMainScreen(Modifier, navController)
        }
    }
}