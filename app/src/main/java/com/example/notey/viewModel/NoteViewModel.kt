package com.example.notey.viewModel

import kotlinx.coroutines.flow.Flow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notey.repository.NotesRepository
import com.example.notey.roomdb.Note
import kotlinx.coroutines.launch

class NoteViewModel(private  val repository: NotesRepository): ViewModel() {
    val allNotes: Flow<List<Note>> = repository.allNotes

    fun insert(note: Note) =
        viewModelScope.launch {
            repository.insertNote(note)
        }
}