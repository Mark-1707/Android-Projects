package com.mark.notes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mark.notes.room.Note

class MainActivity : AppCompatActivity(), NotesRVAdapter.INotesRVAdapter {

    lateinit var viewModel: NoteViewModel
    lateinit var recyclerView : RecyclerView
    lateinit var submit: Button
    lateinit var input: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        submit = findViewById(R.id.submit)
        input = findViewById(R.id.input)
        recyclerView = findViewById(R.id.recyclerView)

        recyclerView.layoutManager = LinearLayoutManager(this)
        val adapter = NotesRVAdapter(this,this)
        recyclerView.adapter = adapter

        submit.setOnClickListener {
            val noteText =input.text.toString()
            if(noteText.isNotEmpty()){
                viewModel.insertNote(Note(noteText))
                Toast.makeText(this,"Inserted",Toast.LENGTH_SHORT).show()
            }
        }

        viewModel = ViewModelProvider(this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)).get(NoteViewModel::class.java)

        viewModel.allNotes.observe(this, Observer {list ->
            list?.let {
                adapter.updateList(it)
            }
        })
    }
    override fun onItemClicked(note: Note) {
        viewModel.deleteNote(note)
        Toast.makeText(this,"Deleted",Toast.LENGTH_SHORT).show()
    }
}