package com.example.habitgarden

import android.media.MediaPlayer
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class FocusTimerActivity : AppCompatActivity() {

    private lateinit var timer: CountDownTimer
    private lateinit var mediaPlayer: MediaPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_focus_timer)

        val txtTimer = findViewById<TextView>(R.id.txtTimer)
        val btnStartTimer = findViewById<Button>(R.id.btnStartTimer)
        val btnEndSession = findViewById<Button>(R.id.btnEndSession)

        try {
            mediaPlayer = MediaPlayer.create(this, R.raw.nature_sound)
            mediaPlayer.isLooping = true
        } catch (e: Exception) {
            Toast.makeText(this, "Error loading sound", Toast.LENGTH_SHORT).show()
        }


        btnStartTimer.setOnClickListener {
            mediaPlayer.start()
            startFocusTimer(txtTimer)
        }

        btnEndSession.setOnClickListener {
            mediaPlayer.stop()
            timer.cancel()
            finish()
        }
    }

    private fun startFocusTimer(txtTimer: TextView) {
        timer = object : CountDownTimer(1500000, 1000) { // 25 minutes
            override fun onTick(millisUntilFinished: Long) {
                val minutes = (millisUntilFinished / 1000) / 60
                val seconds = (millisUntilFinished / 1000) % 60
                txtTimer.text = String.format("%02d:%02d", minutes, seconds)
            }

            override fun onFinish() {
                txtTimer.text = "00:00"
                mediaPlayer.stop()
                showGrowthAnimation()
            }
        }.start()
    }

    private fun showGrowthAnimation() {
        // Placeholder for animation logic
        Toast.makeText(this, "Plant grew a bit!", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.release()
    }
}
