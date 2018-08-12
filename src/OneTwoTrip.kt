import java.io.File
import java.lang.Integer.valueOf
import java.util.*

fun main(args: Array<String>) {
    outputToFile("output.txt", makeUpWordFromFile("input.txt", "OneTwoTrip"))
}

// Time Complexity: O(M * N) where M is the number of rows in the matrix, N is the number of columns in the matrix
// Space Complexity: O(N) where N is the number of columns in the matrix
private fun makeUpWordFromFile(fileName: String, word: String) = File(fileName).bufferedReader().use { input ->
    val (rowsNumber, columnsNumber) = input.readLine().split(" ").map(::valueOf)
    if (word.length > rowsNumber * columnsNumber) {
        return emptyList<String>()
    }

    val result = MutableList(word.length) { "" }
    val searchedChars = generateSearchedChars(word)

    var numberFound = 0
    for (row in 0 until rowsNumber) {
        for ((column, char) in input.readLine().withIndex()) {
            val positionsInWord = searchedChars[char.toLowerCase()]
            if (positionsInWord?.isEmpty() == false) {
                result[positionsInWord.poll()] = "$char - ($row, $column);"
                if (++numberFound == word.length) {
                    return result
                }
            }
        }
    }
    emptyList<String>()
}

// Time Complexity: O(N) where N is the number of chars in the word
// Space Complexity: O(N) where N is the number of chars in the word
private fun generateSearchedChars(word: String): Map<Char, LinkedList<Int>> {
    val result = hashMapOf<Char, LinkedList<Int>>()
    return word.map { it.toLowerCase() }.withIndex()
            .associateByTo(result, { it.value }) {
                val list = result[it.value] ?: LinkedList()
                list.apply { add(it.index) }
            }
}

private fun outputToFile(fileName: String, word: List<String>) = File(fileName).bufferedWriter().use { output ->
    if (word.isEmpty()) {
        output.write("Impossible")
    } else {
        word.forEach { output.write(it); output.newLine() }
    }
}
