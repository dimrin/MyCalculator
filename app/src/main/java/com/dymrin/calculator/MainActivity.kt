package com.dymrin.calculator

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.dymrin.calculator.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

//    TODO clean code and move parts to other packages, add top bar, set up app bar,
//     add button for history, add another activity for history,
//     add room to save and show the history

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
                setZero(view)
            }
            btnDoubleZero.setOnClickListener { view ->
                setZero(view)
            }
        }
    }

    private fun initSpecialButtons() {
        binding.apply {
            btnClr.setOnClickListener {
                clearTheLine()
            }
            btnPercent.setOnClickListener {
                doPercent()
            }
            btnBackspace.setOnClickListener {
                doBackspace()
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
            btnDot.setOnClickListener {
                setDecimalPoint()
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
        lastNumeric = false
        lastDot = false
    }

    private fun doPercent() {

        var isStartFromMinus = false

        if (lastNumeric) {

            var line = binding.tvInput.text.toString()
            if (line.startsWith("-")) {
                line = line.substring(1)
                isStartFromMinus = true
            }
            if (isOperatorAdded(line)) {
                if (line.contains("/") or line.contains("x")) {
                    val splitLineFor: List<String> = line.split("-", "+", "x", "/")
                    val resultFor = (splitLineFor[1].toDouble() / 100).toString()
                    line = line.replaceRange(
                        line.length - splitLineFor[1].length,
                        line.lastIndex + 1,
                        resultFor
                    )
                    if (isStartFromMinus) {
                        line = "-$line"
                    }
                    getTheResult(line)
                    return
                }

                if (isStartFromMinus) {
                    if (line.contains("-")) {
                        line = line.replace("-", "+")
                    } else if (line.contains("+")) {
                        line = line.replace("+", "-")
                    }
                }

                Toast.makeText(this, line, Toast.LENGTH_LONG).show()

                val splitLine: List<String> = line.split("-", "+", "x", "/")
                val result = (splitLine[0].toDouble() / 100 * splitLine[1].toDouble()).toString()

                line =
                    line.replaceRange(line.length - splitLine[1].length, line.lastIndex + 1, result)

                if (isStartFromMinus) {
                    line = "-$line"
                }
                getTheResult(line)

            } else {
                binding.tvInput.text = (line.toDouble() / 100).toString()
            }

        }
    }

    private fun setDecimalPoint() {

        if (lastNumeric || !lastDot) {
            val line = binding.tvInput.text

            if (isOperatorAdded(line.toString())) {
                val splitLine = line.substring(1).split("-", "+", "x", "/")
                if (!splitLine[1].contains(".")) {
                    if (!lastNumeric) {
                        binding.tvInput.append("0.")
                    } else {
                        binding.tvInput.append(".")
                    }
                }
                Toast.makeText(this, splitLine[1], Toast.LENGTH_LONG).show()
            } else {
                if (line.substring(1).isEmpty() && line.startsWith("-")) {
                    if (!line.contains(".")) {
                        binding.tvInput.append("0.")
                    } else {
                        binding.tvInput.append(".")
                    }
                } else {
                    if (!line.contains(".")) {
                        binding.tvInput.append(".")
                    }
                }
            }
            lastNumeric = false
            lastDot = true
        }

    }

    private fun setZero(view: View) {
        val line = binding.tvInput.text
        if (line.isEmpty()) {
            binding.tvInput.append(0.toString())
        } else {
            if (line.toString() != "0") {
                setNumber(view)
            }
        }
    }

    private fun setOperator(view: View) {
        getTheResult()
        binding.tvInput.text.let {
            if ((view as Button).text == "-") {
                if (it.isNullOrEmpty()) {
                    binding.tvInput.append("-")
                }
            }
            if (lastNumeric && !isOperatorAdded(it.toString())) {
                binding.tvInput.append(view.text)
                lastNumeric = false
                lastDot = false
            }
        }
    }


    private fun getTheResult(lineToCalculate: String = binding.tvInput.text.toString()) {
        val plus = "+"
        val minus = "-"
        val multiply = "x"
        val divide = "/"

        if (lastNumeric) {
            var tvValue = lineToCalculate
            var prefix = ""
            try {
                if (tvValue.startsWith("-")) {
                    prefix = "-"
                    tvValue = tvValue.substring(1)
                }
                when {
                    tvValue.contains(minus) -> binding.tvInput.text =
                        removeZeroAfterDot(
                            (splitForCalculate(minus, tvValue, prefix)[0]
                                    - splitForCalculate(
                                minus,
                                tvValue,
                                prefix
                            )[1]).toString()
                        )
                    tvValue.contains(plus) -> binding.tvInput.text =
                        removeZeroAfterDot(
                            (splitForCalculate(plus, tvValue, prefix)[0]
                                    + splitForCalculate(
                                plus,
                                tvValue,
                                prefix
                            )[1]).toString()
                        )
                    tvValue.contains(divide) -> binding.tvInput.text =
                        removeZeroAfterDot(
                            (splitForCalculate(divide, tvValue, prefix)[0]
                                    / splitForCalculate(
                                divide,
                                tvValue,
                                prefix
                            )[1]).toString()
                        )
                    tvValue.contains(multiply) -> binding.tvInput.text =
                        removeZeroAfterDot(
                            (splitForCalculate(multiply, tvValue, prefix)[0]
                                    * splitForCalculate(
                                multiply,
                                tvValue,
                                prefix
                            )[1]).toString()
                        )
                }
            } catch (e: java.lang.ArithmeticException) {
                e.printStackTrace()
            }
        }
    }

    private fun splitForCalculate(
        operator: String,
        lineToSplit: String,
        prefix: String
    ): Array<Double> {
        val splitValue = lineToSplit.split(operator)
        var one = splitValue[0]
        val two = splitValue[1]
        return if (two.isNotEmpty()) {
            if (prefix.isNotEmpty()) {
                one = prefix + one
            }

            arrayOf(one.toDouble(), two.toDouble())
        } else arrayOf(one.toDouble(), 0.0)

    }

    private fun doBackspace() {
        val line = binding.tvInput.text
        if (line.isNotEmpty()) {
            binding.tvInput.text = deleteLastSymbol(line.toString())
            if (!line.startsWith("-") && line.length > 1) {
                lastNumeric = true
            }
        }
    }

    private fun deleteLastSymbol(result: String): String {
        if (result.endsWith("0.")) {
            return result.substring(0, result.length - 2)
        }
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
        return if (value.startsWith("-") && !value.substring(1).contains("-")) false else {
            value.contains("/") || value.contains("+") || value.contains("x") || value.contains("-")
        }
    }


}