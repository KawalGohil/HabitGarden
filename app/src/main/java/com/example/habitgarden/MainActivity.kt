package com.example.habitgarden

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var habitAdapter: HabitAdapter
    private val habitList = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnAddHabit = findViewById<Button>(R.id.btnAddHabit)
        val btnStartFocus = findViewById<Button>(R.id.btnStartFocus)
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewHabits)

        // Set up RecyclerView with the adapter
        habitAdapter = HabitAdapter(habitList)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = habitAdapter

        // Register Activity Result for HabitCreationActivity
        val habitCreationLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val newHabit = result.data?.getStringExtra("NEW_HABIT")
                if (!newHabit.isNullOrEmpty()) {
                    addNewHabit(newHabit)
                }
            }
        }

        btnAddHabit.setOnClickListener {
            val intent = Intent(this, HabitCreationActivity::class.java)
            habitCreationLauncher.launch(intent)
        }

        btnStartFocus.setOnClickListener {
            startActivity(Intent(this, FocusTimerActivity::class.java))
        }
    }

    private fun addNewHabit(habit: String) {
        habitList.add(habit) // Add the new habit to the list
        habitAdapter.notifyItemInserted(habitList.size - 1) // Notify RecyclerView of the change
    }
}
