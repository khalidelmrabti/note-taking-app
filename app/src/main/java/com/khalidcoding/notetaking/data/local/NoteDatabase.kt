package com.khalidcoding.notetaking.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.khalidcoding.notetaking.data.model.Note
import com.khalidcoding.notetaking.data.model.ArchiveNote
import com.khalidcoding.notetaking.utils.DATABASE_VERSION
import com.khalidcoding.notetaking.utils.DateConverter

@Database(entities = [Note::class,ArchiveNote::class], version = DATABASE_VERSION, exportSchema = false)
@TypeConverters(DateConverter::class)
abstract class NoteDatabase : RoomDatabase() {
    abstract fun noteDao() : NoteDao
}