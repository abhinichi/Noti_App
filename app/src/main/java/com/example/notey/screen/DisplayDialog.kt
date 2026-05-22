package com.example.notey.screen
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import com.example.notey.roomdb.Note
import com.example.notey.viewModel.NoteViewModel

@Composable
fun DisplayDialog(viewModel: NoteViewModel, showDialog: Boolean, onDismiss: () -> Unit) {
    var title by remember { mutableStateOf("") }
    var description  by remember { mutableStateOf("") }
    var selectedColor by remember { mutableStateOf(Color.Blue) }

    if(showDialog){
    AlertDialog(
        onDismissRequest = onDismiss ,
        title = { Text(text = "Add Note") },
        text = {
            Column() {
                TextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Note Title") }
                )
                Spacer(
                    modifier = Modifier.height(14.dp)
                )
                TextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Note Description") }
                )
                Spacer(
                    modifier = Modifier.height(14.dp)
                )
                MyColorPicker(selectedColor = selectedColor, onColorSelected = {selectedColor = it})
            }
        },
        confirmButton = {
            Button(onClick = {
                    val newNote = Note(
                        id = 0,
                        title = title,
                        description = description,
                        color = selectedColor.toArgb()
                    )
                    if(title.isNotEmpty() && description.isNotEmpty())
                    {viewModel.insert(newNote)}

                onDismiss()
            }) { Text(text = "Save Note") }
        },
        dismissButton = {
   Button(onClick = onDismiss
   ) { Text( text = "Cancel") }
        }
    )}

}