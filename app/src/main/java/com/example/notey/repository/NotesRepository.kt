package com.example.notey.repository

import androidx.lifecycle.LiveData
import com.example.notey.roomdb.Note
import com.example.notey.roomdb.NoteDao

class NotesRepository(private val notesDao: NoteDao) {
val allNotes : LiveData<List<Note>> = notesDao.getAllNotes()

    suspend fun insertNote(note: Note){
        return notesDao.insert(note)
    }
}