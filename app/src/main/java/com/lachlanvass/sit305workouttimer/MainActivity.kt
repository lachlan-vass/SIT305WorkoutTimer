package com.lachlanvass.sit305workouttimer

import android.os.Bundle
import android.os.SystemClock
import android.widget.Button
import android.widget.Chronometer
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val startButton = findViewById<Button>(R.id.start_button)
        val pauseButton = findViewById<Button>(R.id.pause_button)
        val stopButton = findViewById<Button>(R.id.stop_button)

        var timeWhenStopped: Long = 0
        val timer = findViewById<Chronometer>(R.id.timer_display)
        timer.format = "Time Running - %s"
        timer.base = SystemClock.elapsedRealtime()

        val workoutSummary = findViewById<TextView>(R.id.workout_summary)
        val taskInput = findViewById<EditText>(R.id.task_name_input)

        startButton.setOnClickListener {

            timer.base = SystemClock.elapsedRealtime() + timeWhenStopped;
            timer.start()
        }

        pauseButton.setOnClickListener {

            timeWhenStopped = timer.base - SystemClock.elapsedRealtime();
            timer.stop()
            println("Pause button")
        }

        stopButton.setOnClickListener {

            timer.stop()

            timeWhenStopped = SystemClock.elapsedRealtime() - timer.base

            val minutes = timeWhenStopped / 1000 / 60
            val seconds = timeWhenStopped / 1000 % 60

            var taskName:String

            with (taskInput.text) {
                taskName = when {
                    isEmpty() -> "NO_TASK_NAME"
                    else -> taskInput.text.toString()
                }
            }
            workoutSummary.text = "You spent $minutes:$seconds on $taskName"
            timer.base = SystemClock.elapsedRealtime();
            timeWhenStopped = 0;
        }


    }
}