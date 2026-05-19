package com.example.notey.repository

import androidx.lifecycle.LiveData
import kotlinx.coroutines.flow.Flow
import com.example.notey.roomdb.Note
import com.example.notey.roomdb.NoteDao

class NotesRepository(private val notesDao: NoteDao) {
val allNotes : Flow<List<Note>> = notesDao.getAllNotes()

    suspend fun insertNote(note: Note){
        return notesDao.insert(note)
    }
}