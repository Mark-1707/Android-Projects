package com.mark.birthdaywishing

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.createBirthdayButton).setOnClickListener {
            val name = findViewById<EditText>(R.id.nameInput).editableText.toString()

            val intent = Intent(this,BirthdayGreetingActivity::class.java)
            intent.putExtra(BirthdayGreetingActivity.NAME_EXTRA,name)
            startActivity(intent)
        }
    }
}