package com.khalidcoding.notetaking.di

import android.content.Context
import androidx.room.Room
import com.khalidcoding.notetaking.data.local.NoteDatabase
import com.khalidcoding.notetaking.data.local.NoteDao
import com.khalidcoding.notetaking.repositories.DefaultNoteRepository
import com.khalidcoding.notetaking.repositories.NoteRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideNotesDao(noteDatabase: NoteDatabase): NoteDao = noteDatabase.noteDao()

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): NoteDatabase =
        Room.databaseBuilder(context, NoteDatabase::class.java, "notes_db")
            .fallbackToDestructiveMigration().build()

    @Singleton
    @Provides
    fun provideDefaultNoteRepository(
        dao: NoteDao
    ): NoteRepository = DefaultNoteRepository(dao)

}
