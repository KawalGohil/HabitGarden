package com.example.habitgarden

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class HabitAdapter(private val habits: MutableList<String>,
                   private val onHabitComplete: (Int) -> Unit)
    : RecyclerView.Adapter<HabitAdapter.HabitViewHolder>() {

    class HabitViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val habitName: TextView = view.findViewById(R.id.habitName)
        val completeButton: Button = view.findViewById(R.id.completeButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HabitViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.habit_item, parent, false)
        return HabitViewHolder(view)
    }

    override fun onBindViewHolder(holder: HabitViewHolder, position: Int) {
        holder.habitName.text = habits[position]
        holder.completeButton.setOnClickListener {
            onHabitComplete(position) // Call the onHabitComplete callback
        }
    }

    override fun getItemCount(): Int {
        return habits.size
    }

    fun addHabit(habit: String) {
        habits.add(habit) // Add the habit to the adapter's list
        notifyItemInserted(habits.size - 1) // Notify RecyclerView about the new item
    }
}
