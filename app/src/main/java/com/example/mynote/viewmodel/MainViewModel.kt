package com.example.mynote.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel

/**
 * Simple in‑memory representation of a note. In a real app this would be persisted using Room,
 * DataStore, or another persistence mechanism.
 */
data class Note(
    val id: Int,
    var title: String,
    var content: String
)

/**
 * ViewModel that holds the list of notes and provides CRUD operations.
 * The list is exposed as a [MutableList] wrapped with Compose's state handling so UI recomposes
 * automatically when the data changes.
 */
class MainViewModel : ViewModel() {
    // Backing list of notes – observable by Compose
    private val _notes = mutableStateListOf<Note>()
    val notes: List<Note> get() = _notes

    // Simple auto‑incrementing id generator
    private var nextId = 0

    /** Adds a new note to the list */
    fun addNote(title: String, content: String) {
        val note = Note(id = nextId++, title = title, content = content)
        _notes.add(note)
    }

    /** Updates an existing note identified by [id] */
    fun updateNote(id: Int, newTitle: String, newContent: String) {
        val note = _notes.find { it.id == id } ?: return
        note.title = newTitle
        note.content = newContent
        // Trigger recomposition by replacing the element (Compose tracks list mutations)
        val index = _notes.indexOf(note)
        if (index >= 0) {
            _notes[index] = note
        }
    }

    /** Deletes a note */
    fun deleteNote(id: Int) {
        _notes.removeAll { it.id == id }
    }
}
