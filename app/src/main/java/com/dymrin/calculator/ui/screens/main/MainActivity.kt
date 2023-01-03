package com.dymrin.calculator.ui.screens.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.dymrin.calculator.data.app.CalculatorApp
import com.dymrin.calculator.data.data.dao.HistoryDAO
import com.dymrin.calculator.data.data.repository.HistoryRepository
import com.dymrin.calculator.data.model.HistoryEntity
import com.dymrin.calculator.databinding.ActivityMainBinding
import com.dymrin.calculator.ui.screens.history.HistoryActivity
import com.dymrin.calculator.ui.screens.settings.SettingsActivity
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {

//    TODO clean code and move parts to other packages,

    private lateinit var binding: ActivityMainBinding
    private var lastNumeric = false
    private var lastDot = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        if (supportActionBar != null) {
            supportActionBar?.setDisplayShowTitleEnabled(false)
        }

        val repository = HistoryRepository((application as CalculatorApp).db.historyDao())

        initNumberButtons()
        initSpecialButtons(repository)



        binding.toolbarHistoryBtn.setOnClickListener {
            val intent = Intent(this, HistoryActivity::class.java)
            startActivity(intent)
        }

        binding.toolbarSettingsBtn.setOnClickListener {
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
        }

        binding.tvInput.text = getResultFromHistory()


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

    private fun initSpecialButtons(historyRepository: HistoryRepository) {
        binding.apply {
            btnClr.setOnClickListener {
                clearTheLine()
            }
            btnPercent.setOnClickListener {
                doPercent(historyRepository)
            }
            btnBackspace.setOnClickListener {
                setLineAfterBackspace()
            }
            btnDivide.setOnClickListener { view ->
                setOperator(historyRepository,view)
            }
            btnMultiply.setOnClickListener { view ->
                setOperator(historyRepository,view)
            }
            btnPlus.setOnClickListener { view ->
                setOperator(historyRepository,view)
            }
            btnMinus.setOnClickListener { view ->
                setOperator(historyRepository,view)
            }
            btnEqual.setOnClickListener {
                setTheResult(historyRepository)
            }
            btnDot.setOnClickListener {
                setDecimalPoint()
            }
        }
    }

    private fun getScreenInfo(): String = binding.tvInput.text.toString()

    private fun setNumber(view: View) {
        binding.tvInput.append((view as Button).text)
        lastNumeric = true
        lastDot = false
    }


    private fun clearTheLine() {
        binding.tvInput.text = setEmptyLine()
        lastNumeric = false
        lastDot = false
    }

    fun setEmptyLine(): String {
        return ""
    }

    private fun doPercent(historyRepository: HistoryRepository) {
        var isStartFromMinus = false

        if (lastNumeric) {

            var line = binding.tvInput.text.toString()
            if (line.startsWith("-")) {
                line = line.substring(1)
                isStartFromMinus = true
            }
            if (isOperatorAdded(line)) {
                if (line.contains("/") or line.contains("x")) {
                    val splitLine: List<String> = line.split("-", "+", "x", "/")
                    val result = (splitLine[1].toDouble() / 100).toString()
                    line = line.replaceRange(
                        line.length - splitLine[1].length,
                        line.lastIndex + 1,
                        result
                    )
                    if (isStartFromMinus) {
                        line = "-$line"
                    }
                    setTheResult(historyRepository,line)
                    return
                }

                if (isStartFromMinus) {
                    if (line.contains("-")) {
                        line = line.replace("-", "+")
                    } else if (line.contains("+")) {
                        line = line.replace("+", "-")
                    }
                }

                val splitLine: List<String> = line.split("-", "+", "x", "/")
                val result = (splitLine[0].toDouble() / 100 * splitLine[1].toDouble()).toString()

                line =
                    line.replaceRange(line.length - splitLine[1].length, line.lastIndex + 1, result)

                if (isStartFromMinus) {
                    line = "-$line"
                }
                setTheResult(historyRepository,line)

            } else {
                val result = (line.toDouble() / 100).toString()
                binding.tvInput.text = result
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
            } else {
                if (line.isEmpty() || (line.substring(1).isEmpty() && line.startsWith("-"))) {
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

    private fun setOperator(historyRepository: HistoryRepository,view: View) {
        setTheResult(historyRepository)
        binding.tvInput.text.let {
            if ((view as Button).text == "-") {
                if (it.isEmpty()) {
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

    fun doPlus(splitLine: Array<Double>): String {
        return removeZeroAfterDot(
            (splitLine[0]
                    + splitLine[1]).toString()
        )
    }

    fun doMinus(splitLine: Array<Double>): String {
        return removeZeroAfterDot(
            (splitLine[0]
                    - splitLine[1]).toString()
        )
    }

    fun doDivide(splitLine: Array<Double>): String {
        return removeZeroAfterDot(
            (splitLine[0]
                    / splitLine[1]).toString()
        )
    }

    fun doMultiply(splitLine: Array<Double>): String {
        return removeZeroAfterDot(
            (splitLine[0]
                    * splitLine[1]).toString()
        )
    }

    private fun setTheResult(historyRepository: HistoryRepository,lineToCalculate: String = binding.tvInput.text.toString()) {
        val plus = "+"
        val minus = "-"
        val multiply = "x"
        val divide = "/"

        if (lastNumeric) {
            try {
                when {
                    lineToCalculate.substring(1).contains(minus) -> {
                        val result = doMinus(splitForCalculate(minus, lineToCalculate))
                        addResultToDatabase(historyRepository, "$lineToCalculate = $result")
                        binding.tvInput.text = result
                    }
                    lineToCalculate.contains(plus) -> {
                        val result = doPlus(splitForCalculate(plus, lineToCalculate))
                        addResultToDatabase(historyRepository, "$lineToCalculate = $result")
                        binding.tvInput.text = result
                    }
                    lineToCalculate.contains(divide) -> {

                        val result = doDivide(splitForCalculate(divide, lineToCalculate))
                        addResultToDatabase(historyRepository, "$lineToCalculate = $result")
                        binding.tvInput.text = result
                    }
                    lineToCalculate.contains(multiply) -> {

                        val result = doMultiply(splitForCalculate(multiply, lineToCalculate))
                        addResultToDatabase(historyRepository, "$lineToCalculate = $result")
                        binding.tvInput.text = result
                    }
                }
            } catch (e: java.lang.ArithmeticException) {
                e.printStackTrace()
            }
        }
    }

    private fun splitForCalculate(
        operator: String,
        lineToEdit: String
    ): Array<Double> {

        var lineToSplit = lineToEdit
        var prefix = ""

        if (lineToSplit.startsWith("-")) {
            prefix = "-"
            lineToSplit = lineToSplit.substring(1)
        }

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

    private fun setLineAfterBackspace(){
        binding.tvInput.text = doBackspace(binding.tvInput.text.toString())
    }

    private fun doBackspace(lineToEdit: String): String {
        if (lineToEdit.isNotEmpty()) {
            val result = deleteLastSymbol(lineToEdit)
            if (result.isEmpty()){
                lastNumeric = false
            }
            lastNumeric = !lineToEdit.startsWith("-") && lineToEdit.length > 1
            return result
        }
        return ""
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
        return if (value.startsWith("-") && !value.substring(1).contains("-")) false else {
            value.contains("/") || value.contains("+") || value.contains("x") || value.contains("-")
        }
    }

    private fun addResultToDatabase(historyRepository: HistoryRepository, result: String) {
        lifecycleScope.launch {
            historyRepository.addTheResult(HistoryEntity(result))
        }
    }

    private fun getResultFromHistory(): String {
        val result = intent.getStringExtra("result")
        return if (result != null) {
            lastNumeric = true
            result
        } else {
            ""
        }
    }

}