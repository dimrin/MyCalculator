package com.dymrin.calculator

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.dymrin.calculator.databinding.ActivityHistoryBinding

class HistoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHistoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbarHistory)

        if (supportActionBar != null) {
            supportActionBar?.setDisplayShowTitleEnabled(false)
        }

        binding.toolbarBackPressedBtn.setOnClickListener {
            onBackPressed()
        }

        binding.toolbarDeleteAllBtn.setOnClickListener {
            Toast.makeText(this, "delete", Toast.LENGTH_LONG).show()
        }

        binding.tvNoResultsAvailable.visibility = View.VISIBLE
    }
}