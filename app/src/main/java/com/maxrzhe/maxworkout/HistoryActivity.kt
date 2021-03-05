package com.maxrzhe.maxworkout

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.maxrzhe.maxworkout.adapter.HistoryDateAdapter
import com.maxrzhe.maxworkout.databinding.ActivityHistoryBinding

class HistoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHistoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(layoutInflater)

        with(binding) {
            setContentView(root)

            setSupportActionBar(tbHistory)
            val actionBar = supportActionBar
            actionBar?.setDisplayHomeAsUpEnabled(true)
            actionBar?.title = "HISTORY"

            tbHistory.setNavigationOnClickListener {
                onBackPressed()
            }

            showRecyclerViewHistory()


        }

    }

    private fun showRecyclerViewHistory() {
        val dates = readAllHistory()
        with(binding) {
            if (dates.size > 0) {
                val adapter = HistoryDateAdapter(this@HistoryActivity, dates)
                rvHistory.visibility = View.VISIBLE
                tvNoData.visibility = View.GONE
                tvHistoryCompleted.visibility = View.VISIBLE
                rvHistory.adapter = adapter
                rvHistory.layoutManager = LinearLayoutManager(this@HistoryActivity)
            } else {
                rvHistory.visibility = View.GONE
                tvNoData.visibility = View.VISIBLE
                tvHistoryCompleted.visibility = View.GONE
            }
        }

    }

    private fun readAllHistory(): ArrayList<String> {
        return SQLHelper(this, null).findAllDates()
    }
}