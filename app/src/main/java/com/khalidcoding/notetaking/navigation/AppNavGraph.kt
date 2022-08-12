package com.khalidcoding.notetaking.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.khalidcoding.notetaking.screens.addnote.AddNoteScreen
import com.khalidcoding.notetaking.screens.home.HomeScreen
import com.khalidcoding.notetaking.screens.NoteViewModel
import com.khalidcoding.notetaking.screens.archive.ArchiveScreen
import com.khalidcoding.notetaking.screens.update.UpdateScreen

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppNavGraph(
    navHostController: NavHostController
) {

    val viewModel = hiltViewModel<NoteViewModel>()

    NavHost(
        navController = navHostController,
        startDestination = AppScreens.Home.route
    ){

        composable(route= AppScreens.Home.route){
            HomeScreen(
                navHostController = navHostController,
                viewModel = viewModel
            )
        }

        composable(route= AppScreens.AddNote.route){

            AddNoteScreen(
                navHostController = navHostController,
                viewModel = viewModel
            )
        }

        composable(route= AppScreens.ArchiveScreen.route){
            ArchiveScreen(
                navHostController = navHostController,
                viewModel = viewModel
            )
        }

        val route = AppScreens.UpdateNote.route

        composable(route = "$route/{id}",
            arguments = listOf(navArgument(name = "id") {
                type = NavType.IntType
            })
        ) { navStack ->
            val id = navStack.arguments?.getInt("id")
            UpdateScreen(
                navHostController = navHostController,
                id = id!!,
                viewModel = viewModel
            )
        }

    }

}