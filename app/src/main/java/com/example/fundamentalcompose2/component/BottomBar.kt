package com.example.fundamentalcompose2.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.compose.rememberNavController
import com.example.fundamentalcompose2.model.Routes

@Composable
fun BottomBar(modifier: Modifier = Modifier, navController: NavController) {
    var selectedItem by remember {
        mutableIntStateOf(0)
    }

    val items = listOf(
        Routes.EventScreen.route to Icons.Filled.Search,
        Routes.ActiveEventScreen.route to Icons.Filled.DateRange
    )
    NavigationBar {
        items.forEach { (route, icon) ->
            val selected = navController.currentBackStackEntry?.destination?.route == route
            NavigationBarItem(selected = selected, onClick = {
                navController.navigate(route) {
                    popUpTo(navController.graph.startDestinationId) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            }, icon = { Icon(imageVector = icon, contentDescription = null)
            },
                label = { Text(text = route) }
            )

        }
    }
}

@Preview
@Composable
fun BottomBarPreview() {
    val navController = rememberNavController()
    BottomBar(navController = navController)
}