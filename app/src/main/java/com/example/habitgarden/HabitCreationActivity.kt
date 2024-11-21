package com.example.habitgarden

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class HabitCreationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_habit_creation)

        val editHabitName = findViewById<EditText>(R.id.editHabitName)
        val btnSaveHabit = findViewById<Button>(R.id.btnSaveHabit)

        btnSaveHabit.setOnClickListener {
            val habitName = editHabitName.text.toString()
            if (habitName.isNotEmpty()) {
                val resultIntent = Intent().apply {
                    putExtra("NEW_HABIT", habitName)
                }
                setResult(Activity.RESULT_OK, resultIntent)
                finish() // Close activity
            } else {
                Toast.makeText(this, "Please enter a habit name.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
