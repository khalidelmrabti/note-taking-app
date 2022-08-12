@file:OptIn(
    ExperimentalFoundationApi::class, ExperimentalComposeUiApi::class
)

package com.khalidcoding.notetaking.components

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.khalidcoding.notetaking.data.model.Note
import com.khalidcoding.notetaking.data.model.ArchiveNote
import com.khalidcoding.notetaking.navigation.AppScreens
import com.khalidcoding.notetaking.screens.NoteViewModel
import com.khalidcoding.notetaking.ui.theme.Dark25
import com.khalidcoding.notetaking.ui.theme.Gold25
import com.khalidcoding.notetaking.utils.convertLongToTime
import com.khalidcoding.notetaking.R


@Composable
fun NoteRow(
    note: Note,
    onNoteLongPressed: (Note) -> Unit = {},
    onNoteClicked: (Note) -> Unit = {}
) {

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(6.dp)
            .clip(
                shape = RoundedCornerShape(
                    topEnd = 30.dp,
                    bottomStart = 30.dp
                )
            )
            .combinedClickable(
                onClick = {
                    onNoteClicked(note)
                },
                onLongClick = {
                    onNoteLongPressed(note)
                }
            ),
        color = if (isSystemInDarkTheme()) Dark25 else Gold25,
        elevation = 4.dp
    ) {

        Column(
            modifier = Modifier.padding(10.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Top
        ) {
            Text(
                text = note.title,
                style = MaterialTheme.typography.subtitle2
            )
            Text(
                text = note.body,
                style = MaterialTheme.typography.subtitle1
            )
            Text(
                text = if (note.isUpdated) "Updated at: ${convertLongToTime(note.entryDate.time)}"
                else convertLongToTime(note.entryDate.time),
                style = MaterialTheme.typography.caption
            )

        }

    }

}

@Composable
fun ArchiveNoteRow(
    note: ArchiveNote,
    onNoteLongPressed: () -> Unit = {},
    onNoteClicked: () -> Unit = {}
) {

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(6.dp)
            .clip(
                shape = RoundedCornerShape(
                    topEnd = 30.dp,
                    bottomStart = 30.dp
                )
            )
            .combinedClickable(
                onClick = {
                    onNoteClicked()
                },
                onLongClick = {
                    onNoteLongPressed()
                }
            ),
        color = if (isSystemInDarkTheme()) Dark25 else Gold25,
        elevation = 4.dp
    ) {

        Column(
            modifier = Modifier.padding(10.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Top
        ) {
            Text(
                text = note.title,
                style = MaterialTheme.typography.subtitle2
            )
            Text(
                text = note.body,
                style = MaterialTheme.typography.subtitle1
            )
            Text(
                text = if (note.isUpdated) "Updated at: ${convertLongToTime(note.entryDate.time)}"
                else convertLongToTime(note.entryDate.time),
                style = MaterialTheme.typography.caption
            )

        }

    }

}

@Composable
fun NoteComponent(
    title: MutableState<String>,
    body: MutableState<String>,
    navHostController: NavHostController,
    keyboardController: SoftwareKeyboardController?,
    update: Boolean = false,
    addNote: Boolean = false,
    onAddNewNote: () -> Unit = {},
    onUpdate: () -> Unit = {}
) {

    Column{

        InputField(
            modifier = Modifier.fillMaxWidth(),
            text = title.value,
            label = "title",
            keyboardController = keyboardController,
            onTextChange = {
                title.value = it
            }
        )

        InputField(
            modifier = Modifier.fillMaxWidth(),
            text = body.value,
            label = "body",
            keyboardController = keyboardController,
            onTextChange = {
                body.value = it
            },
            maxLine = 3
        )

    }

    BackHandler {
        if (addNote) {
            onAddNewNote()
        } else if (update) {
            onUpdate()
        }
        navHostController.popBackStack()
    }
}


@Composable
fun SecondAppBar(
    label: String = "Add Note",
    update: Boolean = false,
    addNote: Boolean = false,
    navHostController: NavHostController,
    onUpdate: () -> Unit = {},
    onAddNewNote: () -> Unit = {},
) {

    TopAppBar(
        title = { Text(text = label) },
        navigationIcon = {

            IconButton(onClick = {

                if (update) {
                    onUpdate()
                } else if (addNote) {
                    onAddNewNote()
                }
                navHostController.popBackStack()
            }) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "back")
            }

        },
        elevation = 3.dp,
        backgroundColor = MaterialTheme.colors.surface,
        modifier = Modifier.padding(6.dp)
    )

}


@Composable
fun MainAppBar(
    modifier: Modifier = Modifier,
    navHostController: NavHostController,
    label: String = "Note App",
    viewModel: NoteViewModel
) {

    viewModel.getArchiveNotes()

    TopAppBar(
        title = { Text(text = label) },
        elevation = 3.dp,
        backgroundColor = MaterialTheme.colors.surface,
        modifier = Modifier.padding(6.dp),
        actions = {
            if (viewModel.archiveNoteList.collectAsState().value.isNotEmpty())
                Icon(
                    modifier = Modifier
                        .padding(horizontal = 10.dp)
                        .clickable {
                            navHostController.navigate(AppScreens.ArchiveScreen.route)
                        },
                    painter = if (isSystemInDarkTheme()) painterResource(id = R.drawable.ic_baseline_archive_24_light)
                    else painterResource(id = R.drawable.ic_baseline_archive_24_dark),
                    contentDescription = "Archive"
                )
        }
    )
}


@ExperimentalComposeUiApi
@Composable
fun InputField(
    modifier: Modifier = Modifier,
    text: String,
    maxLine: Int = 1,
    label: String,
    onImeAction: () -> Unit = {},
    onTextChange: (String) -> Unit = {},
    imeAction: ImeAction = ImeAction.Default,
    keyboardController: SoftwareKeyboardController?

) {

    TextField(
        modifier = modifier
            .padding(top = 10.dp, end = 40.dp, start = 40.dp),
        value = text,
        onValueChange = onTextChange,
        label = {
            Text(label)
        },
        keyboardOptions = KeyboardOptions(
            imeAction = imeAction
        ),
        keyboardActions = KeyboardActions(onDone = {
            onImeAction()
            keyboardController?.hide()

        }),
        textStyle = TextStyle(fontSize = 17.sp),
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = Color.Transparent
        ),
        maxLines = maxLine
    )
}



