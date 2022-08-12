package com.khalidcoding.notetaking.screens.home


import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.*
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.khalidcoding.notetaking.R
import com.khalidcoding.notetaking.components.MainAppBar
import com.khalidcoding.notetaking.components.NoteRow
import com.khalidcoding.notetaking.data.model.Note
import com.khalidcoding.notetaking.data.model.ArchiveNote
import com.khalidcoding.notetaking.navigation.AppScreens
import com.khalidcoding.notetaking.screens.NoteViewModel
import kotlinx.coroutines.*
import me.saket.swipe.SwipeAction
import me.saket.swipe.SwipeableActionsBox
import java.time.Instant
import java.util.*


@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    navHostController: NavHostController = rememberNavController(),
    viewModel: NoteViewModel = hiltViewModel()
) {

    val notesFromDb = viewModel.noteList.collectAsState().value

    var archiveNote by remember {
        mutableStateOf(
            ArchiveNote(
                id = 0,
                title = "",
                body = "",
                entryDate = Date.from(Instant.now()),
                isUpdated = false
            )
        )
    }

    val delete = remember {
        mutableStateOf(false)
    }

    val state = remember(delete.value) {
        MutableTransitionState(true)
    }.apply {
        targetState = false
    }

    Scaffold(
        topBar = {
            MainAppBar(
                viewModel = viewModel,
                navHostController = navHostController
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navHostController.navigate(AppScreens.AddNote.route)
                },
                elevation = FloatingActionButtonDefaults.elevation(
                    defaultElevation = 4.dp
                )
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "new note",)
            }

        }) {


        Column {

            Box(
                modifier = Modifier.fillMaxSize()
            ) {

                HomeContent(
                    notesFromDb,
                    onClick = {
                        navHostController.navigate(
                            AppScreens.UpdateNote.route + "/${it.id}"
                        )
                    },
                    onSwipeAction = { note ->

                        viewModel.removeNote(note)

                        archiveNote = ArchiveNote(
                            id = note.id!!,
                            title = note.title,
                            body = note.body,
                            entryDate = note.entryDate,
                            isUpdated = note.isUpdated
                        )

                        viewModel.addArchiveNote(archiveNote)

                        delete.value = true

                    },
                )


                if (delete.value) {

                    androidx.compose.animation.AnimatedVisibility(
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(alignment = Alignment.BottomCenter)
                            .padding(bottom = 90.dp),
                        visibleState = state,
                        enter = fadeIn(
                            animationSpec = tween(
                                durationMillis = 1000
                            )
                        ),
                        exit = fadeOut(
                            animationSpec = tween(
                                delayMillis = 4000
                            )
                        )
                    ) {

                        UndoAction(delete, viewModel, archiveNote)

                    }

                    LaunchedEffect(Unit) {
                        delay(4000)
                        delete.value = false
                    }

                }
            }


        }
    }
}

@Composable
private fun UndoAction(
    delete: MutableState<Boolean>,
    viewModel: NoteViewModel,
    archiveNote: ArchiveNote
) {

    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(20))
            .background(MaterialTheme.colors.background)
            .padding(horizontal = 15.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Text(text = "Note archived")

        Text(
            text = "Undo",
            color = MaterialTheme.colors.primary,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.clickable {
                delete.value = false

                viewModel.addNote(
                    Note(
                        id = archiveNote.id,
                        title = archiveNote.title,
                        body = archiveNote.body,
                        entryDate = archiveNote.entryDate,
                        isUpdated = archiveNote.isUpdated
                    )
                )

                viewModel.removeArchiveNote(archiveNote)
            }
        )
    }
}


@Composable
fun HomeContent(
    notesFromDb: List<Note>,
    onClick: (Note) -> Unit = {},
    onSwipeAction: (Note) -> Unit = {}
) {

    LazyColumn{

        items(notesFromDb) { note ->
            val archive = SwipeAction(
                icon = if (isSystemInDarkTheme()) painterResource(id = R.drawable.ic_baseline_archive_24_light)
                else painterResource(id = R.drawable.ic_baseline_archive_24_dark),
                background = Color.Blue.copy(alpha = 0.1f),
                onSwipe = {
                    onSwipeAction(note)
                }

            )

            SwipeableActionsBox(
                backgroundUntilSwipeThreshold = Color.Transparent,
                modifier = Modifier.fillMaxSize(),
                startActions = listOf(archive),
                swipeThreshold = 250.dp
            ) {
                NoteRow(
                    note = note,
                    onNoteClicked = onClick
                )
            }
        }

    }


}
