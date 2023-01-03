package com.dymrin.calculator.ui.screens.settings

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dymrin.calculator.databinding.ActivitySettingsBinding

class SettingsActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySettingsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbarSettings)

        if (supportActionBar != null) {
            supportActionBar?.setDisplayShowTitleEnabled(false)
        }

        binding.toolbarBackPressedBtn.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

    }
}