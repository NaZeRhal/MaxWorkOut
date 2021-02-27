package com.maxrzhe.maxworkout

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.maxrzhe.maxworkout.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        with(binding) {
            setContentView(binding.root)
            llStart.setOnClickListener(this@MainActivity)
            llToBmiCalc.setOnClickListener(this@MainActivity)
            llToHistory.setOnClickListener(this@MainActivity)
        }


    }

    override fun onClick(v: View?) {
        with(binding) {
            when (v) {
                llStart -> startActivity(Intent(this@MainActivity, ExerciseActivity::class.java))
                llToBmiCalc -> startActivity(Intent(this@MainActivity, CalculatorActivity::class.java))
                llToHistory -> startActivity(Intent(this@MainActivity, HistoryActivity::class.java))
            }
        }
    }
}