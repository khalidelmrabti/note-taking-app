package com.khalidcoding.notetaking.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.khalidcoding.notetaking.data.model.Note
import com.khalidcoding.notetaking.data.model.ArchiveNote
import com.khalidcoding.notetaking.repositories.NoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
/*
import com.khalidcoding.notetaking.repositories.NoteRepository
*/
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.time.Instant
import java.util.*
import javax.inject.Inject

@HiltViewModel
class NoteViewModel @Inject constructor(
    private val repository: NoteRepository
) : ViewModel() {

    private val _noteList = MutableStateFlow<List<Note>>(emptyList())

    var noteList : StateFlow<List<Note>> = _noteList.asStateFlow()

    private val _note = MutableStateFlow(Note(
        title = "",
        body = "",
        isUpdated = false,
        entryDate = Date.from(Instant.now())
    ))

    var note : StateFlow<Note> = _note



    private val _archiveNoteList = MutableStateFlow<List<ArchiveNote>>(emptyList())

    var archiveNoteList : StateFlow<List<ArchiveNote>> = _archiveNoteList.asStateFlow()


    init {

        viewModelScope.launch(Dispatchers.IO) {
            repository.getAllNotes().distinctUntilChanged().collect { notes ->
                _noteList.value = notes
            }
        }

    }

    fun addNote(note: Note) = viewModelScope.launch {
            repository.addNote(note)
        }



    fun removeNote(note: Note) = viewModelScope.launch { repository.deleteNote(note) }

    fun updateNote(note: Note) = viewModelScope.launch {
        repository.updateNote(note)
    }

    fun getNoteById(id: Int){
        viewModelScope.launch {
         repository.getNoteById(id).distinctUntilChanged().collect {
              _note.value = it
          }
        }
    }

    fun deleteAllNotes() = viewModelScope.launch { repository.deleteAllNotes() }

    fun getNotes(): Flow<List<Note>> = repository.getAllNotes().flowOn(Dispatchers.IO).conflate()


    // Archive

    fun addArchiveNote(note: ArchiveNote) = viewModelScope.launch {
        repository.addArchiveNote(note)

    }

    fun removeArchiveNote(note: ArchiveNote) = viewModelScope.launch {
        repository.deleteArchiveNote(note)
    }


    fun removeArchiveNoteById(id: Int) = viewModelScope.launch {
        repository.deleteArchiveNoteById(id)
    }


    fun getArchiveNotes() {
        viewModelScope.launch {
            repository.getAllArchiveNotes().distinctUntilChanged().collect { notes ->
                _archiveNoteList.value = notes
            }
        }
    }


}
