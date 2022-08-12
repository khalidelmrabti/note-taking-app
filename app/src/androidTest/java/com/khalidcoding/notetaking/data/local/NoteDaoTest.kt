@file:OptIn(ExperimentalCoroutinesApi::class)

package com.khalidcoding.notetaking.data.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.khalidcoding.notetaking.data.model.Note
import com.khalidcoding.notetaking.data.model.ArchiveNote
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.time.Instant
import java.util.*


@RunWith(AndroidJUnit4::class)
@SmallTest
class NoteDaoTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()


    private lateinit var noteDatabase: NoteDatabase
    private lateinit var dao: NoteDao

    @Before
    fun setup() {
        noteDatabase = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            NoteDatabase::class.java
        ).allowMainThreadQueries().build()
        dao = noteDatabase.noteDao()
    }

    @After
    fun teardown() {
        noteDatabase.close()
    }

    @Test
    fun insertNote() = runBlockingTest {
        val note = Note(
            id = Random().nextInt(),
            title = "i love to run",
            body = "a nice run every monday",
            isUpdated = false,
            entryDate = Date.from(Instant.now())
        )

        dao.insertNote(note)

        val savedNote = dao.getNoteById(note.id!!)

        assertThat(savedNote).isEqualTo(note)

    }

    @Test
    fun deleteNote() = runBlockingTest {

        val note = Note(
            id = Random().nextInt(),
            title = "I love gym",
            body = "i go to the gym every day",
            isUpdated = false,
            entryDate = Date.from(Instant.now())
        )

        dao.insertNote(note)
        dao.deleteNote(note)
        val savedNote = dao.getNoteById(note.id!!)
        assertThat(savedNote).isNull()

    }

    @Test
    fun updateNote() = runBlockingTest {

        val originNote = Note(
            id = 9,
            title = "I love gym",
            body = "i go to the gym every day",
            isUpdated = false,
            entryDate = Date.from(Instant.now())
        )

        dao.insertNote(originNote)

        val updatedNote = Note(
            id = originNote.id,
            title = "I love gym",
            body = "i go to the gym 3 days per week",
            isUpdated = true,
            entryDate = Date.from(Instant.now())
        )

        dao.updateNote(updatedNote)
        val noteFromDb = dao.getNoteById(originNote.id!!).first()
        assertThat(noteFromDb.body).isNotEqualTo(originNote.body)

    }

    @Test
    fun deleteAllNotes() = runBlockingTest {

        val note = Note(
            id = Random().nextInt(),
            title = "I love gym",
            body = "i go to the gym every day",
            isUpdated = false,
            entryDate = Date.from(Instant.now())
        )

        val note2 = Note(
            id = Random().nextInt(),
            title = "I love food",
            body = "i eat every hour",
            isUpdated = false,
            entryDate = Date.from(Instant.now())
        )

        dao.insertNote(note)
        dao.insertNote(note2)
        dao.deleteAllNotes()
        val result = dao.getAllNotes().first()
        assertThat(result).isEmpty()

    }

    @Test
    fun getNoteById() = runBlockingTest {

        val note = Note(
            id = 5,
            title = "I love gym",
            body = "i go to the gym every day",
            isUpdated = false,
            entryDate = Date.from(Instant.now())
        )

        val note2 = Note(
            id = 1,
            title = "I love gym",
            body = "i go to the gym every day",
            isUpdated = false,
            entryDate = Date.from(Instant.now())
        )


        dao.insertNote(note)
        dao.insertNote(note2)
        val notById =  dao.getNoteById(5)
        assertThat(notById).isEqualTo(note)

    }


    // Archive

    @Test
    fun insertArchiveNote() = runBlockingTest {
        val note = ArchiveNote(
            id = Random().nextInt(),
            title = "I love to run",
            body = "I run every monday",
            isUpdated = false,
            entryDate = Date.from(Instant.now())
        )

        dao.insertArchiveNote(note)

        val savedNote = dao.getArchiveNotes().first().first()
        assertThat(savedNote).isEqualTo(note)

    }

    @Test
    fun deleteArchiveNote() = runBlockingTest {

        val note = ArchiveNote(
            id = Random().nextInt(),
            title = "I love gym",
            body = "I go to the gym every day",
            isUpdated = false,
            entryDate = Date.from(Instant.now())
        )

        dao.insertArchiveNote(note)
        dao.deleteArchiveNote(note)
        val savedNote = dao.getArchiveNotes().first()
        assertThat(savedNote).isEmpty()

    }

}