package com.khalidcoding.notetaking.data.local

import androidx.room.*
import com.khalidcoding.notetaking.data.model.Note
import com.khalidcoding.notetaking.data.model.ArchiveNote
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: Note)

    @Update
    suspend fun updateNote(note: Note)

    @Delete
    suspend fun deleteNote(note: Note)

    @Query("SELECT * from note_table WHERE id == :id")
    fun getNoteById(id:Int) : Flow<Note>

    @Query("DELETE from note_table")
    suspend fun deleteAllNotes()

    @Query("SELECT * from note_table ORDER BY id DESC ")
    fun getAllNotes() : Flow<List<Note>>


    // Archive

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArchiveNote(note: ArchiveNote)

    @Query("SELECT * from note_archive_tb")
    fun getArchiveNotes() : Flow<List<ArchiveNote>>

    @Delete
    suspend fun deleteArchiveNote(note: ArchiveNote)

    @Query("DELETE from note_archive_tb WHERE id == :id")
    suspend fun deleteArchiveNoteById(id: Int)


}