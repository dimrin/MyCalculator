package com.dymrin.calculator.data.utils

fun setEmptyLine(): String {
    return ""
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

fun removeZeroAfterDot(result: String): String {
    var value = result
    if (result.contains(".0")) {
        value = result.substring(0, result.length - 2)
    }
    return value
}

fun isOperatorAdded(value: String): Boolean {
    return if (value.startsWith("-") && !value.substring(1).contains("-")) false else {
        value.contains("/") || value.contains("+") || value.contains("x") || value.contains("-")
    }
}

fun deleteLastSymbol(result: String): String {
    return result.substring(0, result.length - 1)
}

fun splitForCalculate(
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
