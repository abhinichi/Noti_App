package com.example.notey

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.notey.data.AppDatabase
import com.example.notey.data.Note
import com.example.notey.ui.NoteViewModel
import com.example.notey.ui.theme.NoteyTheme
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        
        val database = AppDatabase.getDatabase(this)
        val viewModelFactory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return NoteViewModel(database.noteDao()) as T
            }
        }

        setContent {
            NoteyTheme {
                val viewModel: NoteViewModel = viewModel(factory = viewModelFactory)
                val notes by viewModel.allNotes.collectAsState()
                
                NoteApp(
                    notes = notes,
                    onAddNote = { title, content -> viewModel.addNote(title, content) },
                    onDeleteNote = { note -> viewModel.deleteNote(note) }
                )
            }
        }
    }
}

@Composable
fun NoteApp(
    notes: List<Note>,
    onAddNote: (String, String) -> Unit,
    onDeleteNote: (Note) -> Unit
) {
    var showDialog by remember { mutableStateOf(false) }
    
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { showDialog = true }) {
                Icon(Icons.Default.Add, contentDescription = "Add Note")
            }
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding).fillMaxSize()) {
            Text(
                text = "My Notes",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(16.dp)
            )
            
            LazyColumn {
                items(notes) { note ->
                    NoteItem(note, onDeleteNote)
                }
            }
        }
        
        if (showDialog) {
            AddNoteDialog(
                onDismiss = { showDialog = false },
                onConfirm = { title, content ->
                    onAddNote(title, content)
                    showDialog = false
                }
            )
        }
    }
}

@Composable
fun NoteItem(note: Note, onDelete: (Note) -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(8.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = note.title, style = MaterialTheme.typography.titleMedium)
                Text(text = note.content, style = MaterialTheme.typography.bodySmall)
            }
            IconButton(onClick = { onDelete(note) }) {
                Icon(Icons.Default.Delete, contentDescription = "Delete")
            }
        }
    }
}

@Composable
fun AddNoteDialog(onDismiss: () -> Unit, onConfirm: (String, String) -> Unit) {
    var title by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }
    
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Add Note") },
        text = {
            Column {
                TextField(value = title, onValueChange = { title = it }, label = { Text("Title") })
                Spacer(modifier = Modifier.height(8.dp))
                TextField(value = content, onValueChange = { content = it }, label = { Text("Content") })
            }
        },
        confirmButton = {
            Button(onClick = { onConfirm(title, content) }) {
                Text("Add")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}