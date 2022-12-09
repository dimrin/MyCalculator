package com.dymrin.calculator

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.dymrin.calculator.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    var lastNumeric = false
    var lastDot = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    fun onDigit(view: View) {
        binding.tvInput.append((view as Button).text)
        lastNumeric = true
        lastDot = false
    }

    fun onClear(view: View) {
        binding.tvInput.text = ""
    }

    fun onDecimalPoint(view: View) {
        if (!binding.tvInput.text.contains(".")) {
            binding.tvInput.append(".")
        }
//        if (!binding.tvInput.text.split(".").isEmpty()){
//            val text = binding.tvInput.text.split('/', '+', '-', '*')[1]
//            if (!text.contains(".")){
//                binding.tvInput.append(".")
//            }
//        }

//        if (lastNumeric || !lastDot){
//            binding.tvInput.append(".")
//            lastNumeric = false
//            lastDot = true
//        }

    }

    fun onOperator(view: View) {
        binding.tvInput.text.let {
            if (lastNumeric && !isOperatorAdded(it.toString())) {
                binding.tvInput.append((view as Button).text)
                lastNumeric = false
                lastDot = false
            }
        }

    }

    fun onEqual(view: View) {
//        написать по новой с использованием string.endsWith

        if (lastNumeric) {
            var tvValue = binding.tvInput.text
            var prefix = ""
            try {

                if (tvValue.startsWith("-")) {
                    prefix = "-"
                    tvValue = tvValue.substring(1)
                }

                if (tvValue.contains("-")) {
                    val splitValue = tvValue.split("-")
                    var one = splitValue[0]
                    var two = splitValue[1]

                    if (prefix.isNotEmpty()) {
                        one = prefix + one
                    }

                    binding.tvInput.text =
                        removeZeroAfterDot((one.toDouble() - two.toDouble()).toString())
                } else if (tvValue.contains("+")) {
                    val splitValue = tvValue.split("+")
                    var one = splitValue[0]
                    var two = splitValue[1]

                    if (prefix.isNotEmpty()) {
                        one = prefix + one
                    }

                    binding.tvInput.text =
                        removeZeroAfterDot((one.toDouble() + two.toDouble()).toString())
                } else if (tvValue.contains("/")) {
                    val splitValue = tvValue.split("/")
                    var one = splitValue[0]
                    var two = splitValue[1]

                    if (prefix.isNotEmpty()) {
                        one = prefix + one
                    }

                    binding.tvInput.text =
                        removeZeroAfterDot((one.toDouble() / two.toDouble()).toString())
                } else if (tvValue.contains("X")) {
                    val splitValue = tvValue.split("X")
                    var one = splitValue[0]
                    var two = splitValue[1]

                    if (prefix.isNotEmpty()) {
                        one = prefix + one
                    }

                    binding.tvInput.text =
                        removeZeroAfterDot((one.toDouble() * two.toDouble()).toString())
                }

            } catch (e: java.lang.ArithmeticException) {
                e.printStackTrace()
            }
        }
    }

    private fun removeZeroAfterDot(result: String): String {
        var value = result
        if (result.contains(".0")) {
            value = result.substring(0, result.length - 2)
        }
        return value
    }


    private fun isOperatorAdded(value: String): Boolean {
        return if (value.startsWith("-")) false else {
            value.contains("/") || value.contains("+") || value.contains("*") || value.contains("-")
        }
    }

}