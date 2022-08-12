@file:OptIn(ExperimentalComposeUiApi::class, ExperimentalComposeUiApi::class)

package com.khalidcoding.notetaking.screens.addnote

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.khalidcoding.notetaking.components.SecondAppBar
import com.khalidcoding.notetaking.components.NoteComponent
import com.khalidcoding.notetaking.data.model.Note
import com.khalidcoding.notetaking.screens.NoteViewModel
import java.time.Instant
import java.util.*

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AddNoteScreen(
    modifier: Modifier = Modifier,
    navHostController: NavHostController,
    viewModel: NoteViewModel = hiltViewModel()
) {

    val title = remember {
        mutableStateOf("")
    }

    val body = remember {
        mutableStateOf("")
    }

    val keyboardController = LocalSoftwareKeyboardController.current

    Scaffold(topBar = {

        SecondAppBar(
            label = "Add note",
            addNote = true,
            onAddNewNote = {
                insertNote(viewModel, title, body)
            },
            navHostController = navHostController
        )
    }) {
        NoteComponent(
            title = title,
            keyboardController = keyboardController,
            body = body,
            addNote = true,
            navHostController = navHostController,
            onAddNewNote = {
                insertNote(viewModel, title, body)
            }
        )

    }

}

@RequiresApi(Build.VERSION_CODES.O)
private fun insertNote(
    viewModel: NoteViewModel,
    title: MutableState<String>,
    body: MutableState<String>,
) {
    viewModel.addNote(
        Note(
            title = title.value,
            body = body.value,
            entryDate = Date.from(Instant.now()),
            isUpdated = false
        )
    )
}

