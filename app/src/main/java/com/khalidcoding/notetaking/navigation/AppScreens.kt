package com.khalidcoding.notetaking.navigation

sealed class AppScreens(
    val route:String,
    val title:String
){
    object Home : AppScreens(
        route = "home",
        title = "Home"
    )

    object AddNote : AppScreens(
        route = "add_note",
        title = "New Note"
    )

    object UpdateNote : AppScreens(
        route = "update_note",
        title = "Update"
    )

    object ArchiveScreen : AppScreens(
        route = "archive",
        title = "Archive"
    )
}
