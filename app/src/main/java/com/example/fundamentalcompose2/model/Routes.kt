package com.example.fundamentalcompose2.model

import okhttp3.Route

sealed class Routes(val route : String) {
    object ActiveEventScreen : Routes("Event")
    object EventScreen : Routes("Search Event")
    object DetailScreen : Routes("Detail")
    object FinishEvent : Routes("finish_event")
}