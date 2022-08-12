package com.khalidcoding.notetaking.repositories

import com.khalidcoding.notetaking.data.model.Note
import com.khalidcoding.notetaking.data.model.ArchiveNote
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import java.time.Instant
import java.util.*

class FakeNoteRepository : NoteRepository {

    private val notesList = mutableListOf<Note>()

    private val observableNotesList = MutableStateFlow<List<Note>>(notesList)

    private val archivedNotesList = mutableListOf<ArchiveNote>()

    private val observableArchivedNotesList = MutableStateFlow<List<ArchiveNote>>(archivedNotesList)


    override suspend fun addNote(note: Note) {
        notesList.add(note)
    }

    override suspend fun deleteNote(note: Note) {
        notesList.remove(note)
    }

    override suspend fun deleteAllNotes() {
        notesList.clear()
    }

    override suspend fun getNoteById(id: Int): Note {
     return notesList.first {
            it.id == id
        }
    }

    override fun getAllNotes(): Flow<List<Note>> {
        return observableNotesList
    }


    override suspend fun updateNote(note: Note) {
        val index = notesList.indexOfFirst {
            note.id == it.id
        }
        notesList.add(index, note)
    }

    // Archived

    override suspend fun addArchiveNote(note: ArchiveNote) {
        archivedNotesList.add(note)
    }

    override suspend fun deleteArchiveNote(note: ArchiveNote) {
        archivedNotesList.remove(note)
    }

    override fun getAllArchiveNotes(): Flow<List<ArchiveNote>> {
        return observableArchivedNotesList
    }

    override suspend fun deleteArchiveNoteById(id: Int) {

    }

}