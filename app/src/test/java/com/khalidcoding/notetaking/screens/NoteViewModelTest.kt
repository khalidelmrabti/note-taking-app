package com.khalidcoding.notetaking.screens

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.khalidcoding.notetaking.MainCoroutineRule
import com.khalidcoding.notetaking.data.model.Note
import com.khalidcoding.notetaking.data.model.ArchiveNote
import com.khalidcoding.notetaking.repositories.FakeNoteRepository
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule

import org.junit.Test
import java.time.Instant
import java.util.*

@OptIn(ExperimentalCoroutinesApi::class)
class NoteViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var viewModel: NoteViewModel

    @Before
    fun setup() {
        viewModel = NoteViewModel(FakeNoteRepository())
    }

    @Test
    fun `insert note item with empty field, should not insert into db`() {

        val note =  Note(5,"new note title","", Date.from(Instant.now()),false)

        viewModel.addNote(note)

        val value = viewModel.noteList.value

        assertThat(value).doesNotContain(note)
    }

    @Test
    fun `insert note item with valid input, should pass`() {

        val note =  Note(5,"new note title","Wow wow", Date.from(Instant.now()),false)

        viewModel.addNote(note)

        val value = viewModel.noteList.value

        assertThat(value).contains(note)
    }

    // Archive

    @Test
    fun `insert archive note with valid input, should pass`() {

        val note =  ArchiveNote(5,"new note title","Wow wow", Date.from(Instant.now()),false)

        viewModel.addArchiveNote(note)
        viewModel.getArchiveNotes().let {
            val value = viewModel.archiveNoteList.value
            assertThat(value).contains(note)
        }

    }
}