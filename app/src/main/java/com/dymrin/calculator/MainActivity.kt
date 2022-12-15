package com.dymrin.calculator

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
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
//                TODO create the fun
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
    }

    private fun setDecimalPoint() {

        if (lastNumeric || !lastDot) {
//            TODO solve problem with if starts with -
            val line = binding.tvInput.text

//            if (!isOperatorAdded(line.toString())){
//                if (line.isEmpty() or line.startsWith("-")) {
//                    if (!line.contains(".") || line == "-") {
//                        binding.tvInput.append("0.")
//                    }
//                } else {
//                    if (!line.contains(".")) {
//                        binding.tvInput.append(".")
//                    }
//                }
//            } else {
//                val splitLine = line.split("-", "+", "x", "/")
//                if (splitLine[1].isEmpty()) {
//                    binding.tvInput.append("0.")
//                } else {
//                    binding.tvInput.append(".")
//                }
//                Toast.makeText(this, splitLine[1], Toast.LENGTH_LONG).show()
//            }

            if (isOperatorAdded(line.toString())) {
                val splitLine = line.split("-", "+", "x", "/")
                if (splitLine[1].isEmpty()) {
                    binding.tvInput.append("0.")
                } else {
                    binding.tvInput.append(".")
                }

                Toast.makeText(this, splitLine[1], Toast.LENGTH_LONG).show()
            } else {
                if (line.isEmpty() or line.startsWith("-")) {
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
                lastNumeric = true
            }
            if (lastNumeric && !isOperatorAdded(it.toString())) {
                binding.tvInput.append(view.text)
                lastNumeric = false
                lastDot = false
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

        if (prefix.isNotEmpty()) {
            one = prefix + one
        }

        return arrayOf(one.toDouble(), two.toDouble())
    }

    private fun getTheResult() {
        val plus = "+"
        val minus = "-"
        val multiply = "x"
        val divide = "/"

        if (lastNumeric) {
            var tvValue = binding.tvInput.text
            var prefix = ""
            try {

                if (tvValue.startsWith("-")) {
                    prefix = "-"
                    tvValue = tvValue.substring(1)
                }

                when {
                    tvValue.contains(minus) -> binding.tvInput.text =
                        removeZeroAfterDot(
                            (splitForCalculate(minus, tvValue.toString(), prefix)[0]
                                    - splitForCalculate(
                                minus,
                                tvValue.toString(),
                                prefix
                            )[1]).toString()
                        )
                    tvValue.contains(plus) -> binding.tvInput.text =
                        removeZeroAfterDot(
                            (splitForCalculate(plus, tvValue.toString(), prefix)[0]
                                    + splitForCalculate(
                                plus,
                                tvValue.toString(),
                                prefix
                            )[1]).toString()
                        )
                    tvValue.contains(divide) -> binding.tvInput.text =
                        removeZeroAfterDot(
                            (splitForCalculate(divide, tvValue.toString(), prefix)[0]
                                    / splitForCalculate(
                                divide,
                                tvValue.toString(),
                                prefix
                            )[1]).toString()
                        )
                    tvValue.contains(multiply) -> binding.tvInput.text =
                        removeZeroAfterDot(
                            (splitForCalculate(multiply, tvValue.toString(), prefix)[0]
                                    * splitForCalculate(
                                multiply,
                                tvValue.toString(),
                                prefix
                            )[1]).toString()
                        )
                }
            } catch (e: java.lang.ArithmeticException) {
                e.printStackTrace()
            }
        }
    }

    private fun doBackspace() {
        val line = binding.tvInput.text
        binding.tvInput.text = deleteLastSymbol(line.toString())
    }

    private fun deleteLastSymbol(result: String): String {
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
            value.contains("/") || value.contains("+") || value.contains("x") || value.contains("-")
        }
    }


}