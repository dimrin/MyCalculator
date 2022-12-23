package com.dymrin.calculator

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.dymrin.calculator.databinding.ActivityHistoryBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

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

        val dao = (application as CalculatorApp).db.historyDao()
        getAllResults(dao)

    }

    private fun getAllResults(historyDAO: HistoryDAO){
        lifecycleScope.launch {
            historyDAO.getResults().collect{ allResults ->
                if (allResults.isNotEmpty()){
                    binding.rvHistory.visibility = View.VISIBLE
                    binding.tvNoResultsAvailable.visibility = View.INVISIBLE

                    binding.rvHistory.layoutManager = LinearLayoutManager(this@HistoryActivity)

                    val results = ArrayList<String>()
                    for (result in allResults){
                        results.add(result.result)
                    }
                    val historyAdapter = HistoryAdapter(results){
                        Toast.makeText(this@HistoryActivity, it, Toast.LENGTH_LONG).show()
                    }
                    binding.rvHistory.adapter = historyAdapter
                } else {
                    binding.rvHistory.visibility = View.GONE
                    binding.tvNoResultsAvailable.visibility = View.VISIBLE
                }

            }
        }
    }
}