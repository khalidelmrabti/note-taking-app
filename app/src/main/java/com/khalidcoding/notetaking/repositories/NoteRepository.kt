package com.khalidcoding.notetaking.repositories

import com.khalidcoding.notetaking.data.model.Note
import com.khalidcoding.notetaking.data.model.ArchiveNote
import kotlinx.coroutines.flow.Flow

interface NoteRepository {

    suspend fun addNote(note: Note)

    suspend fun deleteNote(note: Note)

    suspend fun deleteAllNotes()

    suspend fun getNoteById(id: Int) : Flow<Note>

    fun getAllNotes(): Flow<List<Note>>

    suspend fun updateNote(note: Note)

    // Archived

    suspend fun addArchiveNote(note: ArchiveNote)

    suspend fun deleteArchiveNote(note: ArchiveNote)

    fun getAllArchiveNotes(): Flow<List<ArchiveNote>>

    suspend fun deleteArchiveNoteById(id: Int)
}