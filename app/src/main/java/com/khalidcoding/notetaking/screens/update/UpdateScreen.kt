@file:OptIn(ExperimentalComposeUiApi::class)

package com.khalidcoding.notetaking.screens.update

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.khalidcoding.notetaking.components.SecondAppBar
import com.khalidcoding.notetaking.components.NoteComponent
import com.khalidcoding.notetaking.screens.NoteViewModel
import java.time.Instant
import java.util.*

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun UpdateScreen(
    modifier: Modifier = Modifier,
    navHostController: NavHostController,
    id: Int,
    viewModel: NoteViewModel = hiltViewModel()
) {

    viewModel.getNoteById(id)

    val note = viewModel.note.collectAsState().value

    val title = remember(note) {
        mutableStateOf(note.title)
    }

    val body = remember(note) {
        mutableStateOf(note.body)
    }

    val keyboardController = LocalSoftwareKeyboardController.current

    Scaffold(topBar = {
        SecondAppBar(
            label = "Update",
            update = true,
            onUpdate = {
                note.title = title.value
                note.body = body.value
                note.entryDate = Date.from(Instant.now())
                viewModel.updateNote(note)
            },
            navHostController = navHostController
        )
    }) {

        NoteComponent(
            title = title,
            body = body,
            keyboardController = keyboardController,
            update = true,
            navHostController = navHostController,
            onUpdate = {
                note.title = title.value
                note.body = body.value
                note.entryDate = Date.from(Instant.now())
                note.isUpdated = true
                viewModel.updateNote(note)
            }
        )
    }
}