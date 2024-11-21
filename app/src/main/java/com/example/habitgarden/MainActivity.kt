package com.example.habitgarden

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private var currentPlantStage = 1
    private lateinit var habitAdapter: HabitAdapter
    private val habitList = mutableListOf<String>() // Consistent reference for the adapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnAddHabit = findViewById<Button>(R.id.btnAddHabit)
        val btnStartFocus = findViewById<Button>(R.id.btnStartFocus)
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewHabits)
        val plantImageView = findViewById<ImageView>(R.id.plantImageView)

        // Initialize the adapter with the same mutable list
        habitAdapter = HabitAdapter(habitList) { position ->
            Toast.makeText(this, "Completed: ${habitList[position]}", Toast.LENGTH_SHORT).show()
            growPlant(plantImageView) // Animate the plant when a habit is completed
        }
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
        habitAdapter.addHabit(habit) // Use adapter's method to update the list
    }

    private fun growPlant(plantImageView: ImageView) {
        if (currentPlantStage < 5) { // Ensure it doesn't grow past the final stage
            currentPlantStage++

            plantImageView.setImageResource(getPlantStageDrawable(currentPlantStage))

            // Add a delay to simulate the plant "growing"
            android.os.Handler().postDelayed({
                plantImageView.setImageResource(getPlantStageDrawable(currentPlantStage))
            }, 2500)
        } else {
            Toast.makeText(this, "Plant is fully grown!", Toast.LENGTH_SHORT).show()
        }
    }

    // Helper function to get plant stage drawable
    private fun getPlantStageDrawable(stage: Int): Int {
        return when (stage) {
            1 -> R.drawable.plant_1
            2 -> R.drawable.plant_2
            3 -> R.drawable.plant_3
            4 -> R.drawable.plant_4
            5 -> R.drawable.plant_5
            else -> R.drawable.plant_1
        }
    }
}
