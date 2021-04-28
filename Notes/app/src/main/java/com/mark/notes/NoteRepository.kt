package com.mark.notes

import androidx.lifecycle.LiveData
import com.mark.notes.room.Note
import com.mark.notes.room.NoteDao

class NoteRepository (private val noteDao: NoteDao){

    val allNotes : LiveData<List<Note>> = noteDao.getAllNotes()

    suspend fun insert(note: Note){
        noteDao.insert(note)
    }

    suspend fun delete(note : Note){
        noteDao.delete(note)
    }
}