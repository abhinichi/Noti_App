package com.example.notey.roomdb

import kotlinx.coroutines.flow.Flow
import androidx.room3.Dao
import androidx.room3.Insert
import androidx.room3.Query

@Dao
interface NoteDao {
@Insert
suspend fun insert(note: Note)

@Query("SELECT * FROM notes_table")
fun getAllNotes() : Flow<List<Note>>
}