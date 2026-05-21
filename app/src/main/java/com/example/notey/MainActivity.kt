package com.example.notey

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.core.graphics.toColorInt
import androidx.lifecycle.ViewModelProvider
import com.example.notey.repository.NotesRepository
import com.example.notey.roomdb.Note
import com.example.notey.roomdb.NotesDB
import com.example.notey.screen.DisplayDialog
import com.example.notey.screen.DisplayNotesList
import com.example.notey.ui.theme.NoteyTheme
import com.example.notey.viewModel.NoteViewModel
import com.example.notey.viewModel.NoteViewModelFactory
import kotlin.jvm.java

class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
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

        val note1 = Note(
            id = 0,
            title = "Dummy title",
            description = "This is a dummy description",
            color = "#f59597".toColorInt()
        )
        noteViewModel.insert(note1)
        setContent {
            NoteyTheme {
                Scaffold(floatingActionButton = {
                    MyFlotActBtn(
                        viewModel = noteViewModel
                    )
                }) {
                    val notes by noteViewModel.allNotes.collectAsState(emptyList())
                    DisplayNotesList(notes = notes)
                }
            }
        }
    }
}

@Composable
fun MyFlotActBtn(viewModel: NoteViewModel){

    var showDialog by remember {
        mutableStateOf(false)
    }

    DisplayDialog(viewModel = viewModel, showDialog = showDialog) {
        showDialog = false
    }

    FloatingActionButton(
        onClick = {showDialog = true},
        containerColor = Color.DarkGray,
        contentColor = Color.White
        ) {
        Icon(imageVector = Icons.Filled.Add,
            contentDescription = "Add Note")
    }
}