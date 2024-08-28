package com.example.fundamentalcompose2.component

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar(title: String, icon: ImageVector, context: Context) {
    TopAppBar(
        title = { Text(text = title) },
        actions = {
            Icon(imageVector = icon, contentDescription = null, modifier = Modifier.clickable {
                Toast.makeText(context, "Search", Toast.LENGTH_SHORT).show()
            })
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainer,
            titleContentColor = MaterialTheme.colorScheme.primary
        )
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun TopAppBarPreview() {
    val context = LocalContext.current
    TopAppBar(title = "My Fundamental", icon = Icons.Default.Search, context)
}