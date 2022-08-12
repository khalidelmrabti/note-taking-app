package com.khalidcoding.notetaking.screens.archive

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.khalidcoding.notetaking.R
import com.khalidcoding.notetaking.components.ArchiveNoteRow
import com.khalidcoding.notetaking.components.SecondAppBar
import com.khalidcoding.notetaking.data.model.Note
import com.khalidcoding.notetaking.screens.NoteViewModel
import me.saket.swipe.SwipeAction
import me.saket.swipe.SwipeableActionsBox


@Composable
fun ArchiveScreen(
    navHostController: NavHostController,
    viewModel: NoteViewModel
) {

    viewModel.getArchiveNotes()
    val notesFromDb = viewModel.archiveNoteList.collectAsState().value

    Scaffold(topBar = {
        SecondAppBar(
            navHostController = navHostController,
            label = "Archive"
        )
    }) {
        if (notesFromDb.isNotEmpty())
            LazyColumn {

                items(notesFromDb) { noteArchive ->

                    val delete = SwipeAction(
                        icon = if (isSystemInDarkTheme()) painterResource(id = R.drawable.ic_baseline_delete_sweep_24_light)
                        else painterResource(id = R.drawable.ic_baseline_delete_sweep_24_dark),
                        background = Color.Red.copy(alpha = 0.1f),
                        onSwipe = {
                            viewModel.removeArchiveNote(noteArchive)
                        }

                    )

                    val unarchive = SwipeAction(
                        icon = if (isSystemInDarkTheme()) painterResource(id = R.drawable.ic_baseline_unarchive_24_light)
                        else painterResource(id = R.drawable.ic_baseline_unarchive_24_dark),
                        background = Color.Green.copy(alpha = 0.1f),
                        onSwipe = {
                            viewModel.removeArchiveNote(noteArchive)
                            viewModel.addNote(
                                Note(
                                    id = noteArchive.id,
                                    title = noteArchive.title,
                                    body = noteArchive.body,
                                    isUpdated = noteArchive.isUpdated,
                                    entryDate = noteArchive.entryDate
                                )
                            )
                        }

                    )

                    SwipeableActionsBox(
                        backgroundUntilSwipeThreshold = Color.Transparent,
                        modifier = Modifier.fillMaxSize(),
                        startActions = listOf(delete),
                        endActions = listOf(unarchive),
                        swipeThreshold = 250.dp
                    ) {
                        ArchiveNoteRow(
                            note = noteArchive
                        )
                    }

                }
            }
        else
            Text(text = "You archive is empty :)")
    }
}