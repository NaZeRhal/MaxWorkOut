package com.maxrzhe.maxworkout

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.maxrzhe.maxworkout.databinding.ActivityFinishBinding
import java.text.SimpleDateFormat
import java.util.*

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

        addDateToDB()
    }

    private fun addDateToDB() {
        val calender = Calendar.getInstance()
        val dateTime = calender.time
        Log.i("DATE", "addDateToDB: $dateTime")

        val sdf = SimpleDateFormat("dd MMM yyyy HH:mm:ss", Locale.getDefault())

        val date = sdf.format(dateTime)

        val dbHelper = SQLHelper(this, null)

        dbHelper.addDate(date)

        Log.i("DATE", "date added")
    }


}