package com.dymrin.calculator

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.dymrin.calculator.databinding.ActivityHistoryBinding
import com.dymrin.calculator.databinding.DialogCustomDeleteAllConfirmatonBinding
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

        val dao = (application as CalculatorApp).db.historyDao()

        binding.toolbarBackPressedBtn.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        binding.toolbarDeleteAllBtn.setOnClickListener {
            checkForResults(dao)
        }

        getAllResults(dao) {
            val resultToSent = it.trim().split("=")
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("result", resultToSent[1])
            startActivity(intent)
        }


    }

    private fun getAllResults(historyDAO: HistoryDAO, clickOnItem: (String) -> Unit) {
        lifecycleScope.launch {
            historyDAO.getResults().collect { allResults ->
                if (allResults.isNotEmpty()) {
                    binding.rvHistory.visibility = View.VISIBLE
                    binding.tvNoResultsAvailable.visibility = View.INVISIBLE

                    binding.rvHistory.layoutManager = LinearLayoutManager(this@HistoryActivity)

                    val results = ArrayList<String>()
                    for (result in allResults) {
                        results.add(result.result)
                    }
                    val historyAdapter = HistoryAdapter(results) {
                        clickOnItem.invoke(it)
                    }
                    binding.rvHistory.adapter = historyAdapter
                } else {
                    binding.rvHistory.visibility = View.GONE
                    binding.tvNoResultsAvailable.visibility = View.VISIBLE
                }

            }
        }
    }

    private fun customDialogForBackButton(historyDAO: HistoryDAO) {
        val customDialog = Dialog(this)
        val dialogBinding = DialogCustomDeleteAllConfirmatonBinding.inflate(layoutInflater)
        customDialog.setContentView(dialogBinding.root)
        customDialog.setCanceledOnTouchOutside(false)
        dialogBinding.btnYes.setOnClickListener {
            lifecycleScope.launch {
                historyDAO.deleteAllResults()
            }
            customDialog.dismiss()
        }
        dialogBinding.btnNo.setOnClickListener {
            customDialog.dismiss()
        }

        customDialog.show()
    }

    private fun checkForResults(historyDAO: HistoryDAO) {
        if (binding.tvNoResultsAvailable.visibility == View.VISIBLE) {
            Toast.makeText(this, "No results to delete", Toast.LENGTH_LONG).show()
        } else {
            customDialogForBackButton(historyDAO)
        }
    }


}