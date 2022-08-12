package com.khalidcoding.notetaking.repositories

import com.khalidcoding.notetaking.data.local.NoteDao
import com.khalidcoding.notetaking.data.model.Note
import com.khalidcoding.notetaking.data.model.ArchiveNote
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class DefaultNoteRepository @Inject constructor(
    private val noteDao: NoteDao
    ) : NoteRepository{

    override suspend fun addNote(note: Note) {
        noteDao.insertNote(note)
    }

    override suspend fun deleteNote(note: Note) {
        noteDao.deleteNote(note)
    }

    override suspend fun deleteAllNotes() {
        noteDao.deleteAllNotes()
    }

    override suspend fun getNoteById(id: Int) : Flow<Note>{
       return noteDao.getNoteById(id)
    }

    override fun getAllNotes(): Flow<List<Note>> {
       return noteDao.getAllNotes()
    }

    override suspend fun updateNote(note: Note) {
        noteDao.updateNote(note)
    }

    // Archive

    override suspend fun addArchiveNote(note: ArchiveNote) {
        noteDao.insertArchiveNote(note)
    }

    override suspend fun deleteArchiveNote(note: ArchiveNote) {
       noteDao.deleteArchiveNote(note)
    }

    override fun getAllArchiveNotes(): Flow<List<ArchiveNote>> {
        return noteDao.getArchiveNotes()
    }

    override suspend fun deleteArchiveNoteById(id: Int) {
        noteDao.deleteArchiveNoteById(id)
    }


}
