package com.example.notey.roomdb

import android.content.Context
import androidx.room3.Database
import androidx.room3.Room
import androidx.room3.RoomDatabase
//import kotlin.time.Instant

@Database(entities = [Note::class], version = 1,)
abstract class NotesDB : RoomDatabase() {
    abstract val notesDao : NoteDao

    companion object{
        @Volatile
        private var INSTANCE : NotesDB? = null

        fun getInstance(context: Context): NotesDB {
            synchronized(this) {
                var instance  = INSTANCE
                if (instance == null) {

                    instance = Room.databaseBuilder(
                        context = context.applicationContext,
                        NotesDB:: class.java,
                        name = "notes_db"
                    ).build()
                }
                 INSTANCE = instance
                return instance
            }
        }
    }

}