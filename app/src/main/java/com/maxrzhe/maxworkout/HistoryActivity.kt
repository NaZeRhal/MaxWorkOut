package com.maxrzhe.maxworkout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.maxrzhe.maxworkout.databinding.ActivityHistoryBinding

class HistoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHistoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.tbHistory)
        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.title = "HISTORY"

        binding.tbHistory.setNavigationOnClickListener {
            onBackPressed()
        }
    }
}