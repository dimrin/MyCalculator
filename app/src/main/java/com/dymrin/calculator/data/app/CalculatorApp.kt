package com.dymrin.calculator.data.app

import android.app.Application
import com.dymrin.calculator.data.data.db.HistoryDatabase

class CalculatorApp : Application() {

    val db: HistoryDatabase by lazy {
        HistoryDatabase.getInstance(this)
    }
}