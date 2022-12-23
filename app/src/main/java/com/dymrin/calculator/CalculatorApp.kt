package com.dymrin.calculator

import android.app.Application

class CalculatorApp : Application() {

    val db: HistoryDatabase by lazy {
        HistoryDatabase.getInstance(this)
    }
}