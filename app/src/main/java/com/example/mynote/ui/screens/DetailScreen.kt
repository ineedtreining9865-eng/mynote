package com.example.mynote.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.icons.Icons
import androidx.compose.material3.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.example.mynote.viewmodel.MainViewModel

@Composable
fun DetailScreen(
    viewModel: MainViewModel,
    noteId: Int?,
    onNavigateBack: () -> Unit
) {
    // Determine whether we are editing an existing note or creating a new one
    val isEdit = noteId != null
    val existingNote = viewModel.notes.find { it.id == noteId }

    // UI state – remember across recompositions
    val titleState = remember { mutableStateOf(existingNote?.title ?: "") }
    val contentState = remember { mutableStateOf(existingNote?.content ?: "") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = if (isEdit) "Edit Note" else "New Note") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            TextField(
                value = titleState.value,
                onValueChange = { titleState.value = it },
                label = { Text("Title") },
                modifier = Modifier.fillMaxSize().weight(1f),
                singleLine = true,
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next)
            )
            TextField(
                value = contentState.value,
                onValueChange = { contentState.value = it },
                label = { Text("Content") },
                modifier = Modifier
                    .fillMaxSize()
                    .weight(3f),
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Default)
            )
            Button(
                onClick = {
                    if (isEdit && existingNote != null) {
                        viewModel.updateNote(existingNote.id, titleState.value, contentState.value)
                    } else {
                        viewModel.addNote(titleState.value, contentState.value)
                    }
                    onNavigateBack()
                },
                modifier = Modifier.padding(top = 8.dp)
            ) {
                Text(text = "Save")
            }
        }
    }
}
