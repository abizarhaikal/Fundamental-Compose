package com.example.fundamentalcompose2.model

import okhttp3.Route

sealed class Routes(val route : String) {
    object ActiveEventScreen : Routes("Active Event")
    object EventScreen : Routes("Event")
    object DetailScreen : Routes("detail_screen")
    object FinishEvent : Routes("finish_event")
}