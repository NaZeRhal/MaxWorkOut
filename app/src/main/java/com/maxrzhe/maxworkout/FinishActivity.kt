package com.maxrzhe.maxworkout

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.maxrzhe.maxworkout.databinding.ActivityFinishBinding

class FinishActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFinishBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFinishBinding.inflate(layoutInflater)

        setContentView(binding.root)

        setSupportActionBar(binding.tbFinishActivity)

        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)

        binding.tbFinishActivity.setNavigationOnClickListener {
            onBackPressed()
        }

        binding.btnFinish.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }
}