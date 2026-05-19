package com.example.notey

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.core.graphics.toColorInt
import androidx.lifecycle.ViewModelProvider
import com.example.notey.repository.NotesRepository
import com.example.notey.roomdb.Note
import com.example.notey.roomdb.NotesDB
import com.example.notey.screen.DisplayNotesList
import com.example.notey.ui.theme.NoteyTheme
import com.example.notey.viewModel.NoteViewModel
import com.example.notey.viewModel.NoteViewModelFactory
import kotlin.jvm.java

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val database =  NotesDB.getInstance(applicationContext)

        val repository = NotesRepository(database.notesDao )

        val viewModelFactory = NoteViewModelFactory(repository)

        val noteViewModel = ViewModelProvider(
            this,
            viewModelFactory
        )[NoteViewModel::class.java]

        setContent {
            NoteyTheme {
                val note1 = Note(
                    id = 0,
                    title = "Dummy title",
                    description = "This is a dummy description",
                    color = "#f59597".toColorInt()
                )

                val notes by noteViewModel.allNotes.observeAsState(emptyList())
                DisplayNotesList(notes = notes)
            }
        }
    }
}