package com.dymrin.calculator

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.dymrin.calculator.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var lastNumeric = false
    private var lastDot = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initNumberButtons()
        initSpecialButtons()
    }

    private fun initNumberButtons() {
        binding.apply {
            btnNine.setOnClickListener { view ->
                setNumber(view)
            }
            btnEight.setOnClickListener { view ->
                setNumber(view)
            }
            btnSeven.setOnClickListener { view ->
                setNumber(view)
            }
            btnSix.setOnClickListener { view ->
                setNumber(view)
            }
            btnFive.setOnClickListener { view ->
                setNumber(view)
            }
            btnFour.setOnClickListener { view ->
                setNumber(view)
            }
            btnThree.setOnClickListener { view ->
                setNumber(view)
            }
            btnTwo.setOnClickListener { view ->
                setNumber(view)
            }
            btnOne.setOnClickListener { view ->
                setNumber(view)
            }
            btnZero.setOnClickListener { view ->
                setNumber(view)
            }
            btnDoubleZero.setOnClickListener { view ->
                setNumber(view)
            }
        }
    }

    private fun initSpecialButtons() {
        binding.apply {
            btnClr.setOnClickListener {
                clearTheLine()
            }
            btnPercent.setOnClickListener {
//                TODO create the fun
            }
            btnBackspace.setOnClickListener {
//                TODO create the fun
                test()
            }
            btnDivide.setOnClickListener { view ->
                setOperator(view)
            }
            btnMultiply.setOnClickListener { view ->
                setOperator(view)
            }
            btnPlus.setOnClickListener { view ->
                setOperator(view)
            }
            btnMinus.setOnClickListener { view ->
                setOperator(view)
            }
            btnEqual.setOnClickListener {
                getTheResult()
            }
            btnDot.setOnClickListener { view ->
                setDecimalPoint(view)
            }
        }
    }

    private fun setNumber(view: View) {
        binding.tvInput.append((view as Button).text)
        lastNumeric = true
        lastDot = false
    }


    private fun clearTheLine() {
        binding.tvInput.text = ""
    }

    private fun setDecimalPoint(view: View) {
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

    private fun setOperator(view: View) {
        binding.tvInput.text.let {
            if (lastNumeric && !isOperatorAdded(it.toString())) {
                binding.tvInput.append((view as Button).text)
                lastNumeric = false
                lastDot = false
            }
        }

    }

    private fun getTheResult() {
//       TODO написать по новой с использованием string.endsWith

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

    fun test(){
//        TODO change the name
        val line = binding.tvInput.text
        binding.tvInput.text = remove(line.toString())
    }

    private fun remove(result: String): String {
        return result.substring(0, result.length - 1)
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