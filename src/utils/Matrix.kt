package utils

import java.util.*

fun <T> matrixOf(rowsNumber: Int, columnsNumber: Int, vararg elements: T): Matrix<T> {
    return Matrix(rowsNumber, columnsNumber, elements.asList())
}

inline fun <T> matrixOf(rowsNumber: Int, columnsNumber: Int, init: (Int, Int) -> T): Matrix<T> {
    return Matrix(rowsNumber, columnsNumber, prepareListForMatrix(rowsNumber, columnsNumber, init))
}

inline fun <T> prepareListForMatrix(rowsNumber: Int, columnsNumber: Int, init: (Int, Int) -> T): List<T> {
    val list = ArrayList<T>(rowsNumber * columnsNumber)
    for (row in 0 until rowsNumber) {
        for (column in 0 until columnsNumber) {
            list.add(init(row, column))
        }
    }
    return list
}

class Matrix<out T>(val rowsNumber: Int, val columnsNumber: Int, private val list: List<T>) {

    val size: Int = rowsNumber * columnsNumber

    operator fun get(row: Int, column: Int): T = list[row * columnsNumber + column]

    inline fun forEachIndexed(action: (Int, Int, T) -> Unit) {
        for (row in 0 until rowsNumber) {
            for (column in 0 until columnsNumber) {
                action(row, column, this[row, column])
            }
        }
    }

    override fun toString(): String {
        val sb = StringBuilder()
        sb.append('[')
        forEachIndexed { row, column, value ->
            if (column == 0) sb.append('[')
            sb.append(value.toString())
            if (column == columnsNumber - 1) {
                sb.append(']')
                if (row < rowsNumber - 1) {
                    sb.append(", ")
                }
            } else {
                sb.append(", ")
            }
        }
        sb.append(']')
        return sb.toString()
    }
}
