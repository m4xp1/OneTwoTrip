import utils.Matrix
import utils.matrixOf
import kotlin.math.floor

fun main(args: Array<String>) {
    val matrix = generateIntMatrix(5)
    val result = bypassBySpiral(matrix)

    println(result.joinToString(" "))
}

// Time Complexity: O(N) where N is the size of the matrix
// Space Complexity: O(N) where N is the size of the matrix
private fun bypassBySpiral(matrix: Matrix<Any>): List<Any> {
    require(matrix.size % 2 != 0) { "The size of the matrix should not be even." }

    val trajectory = listOf(Direction.LEFT, Direction.BOTTOM, Direction.RIGHT, Direction.TOP)
    val result = mutableListOf<Any>()

    var x = floor(matrix.columnsNumber / 2f).toInt()
    var y = x

    var directionIndex = 0
    var distance = 1f
    var traveledDistance = 0

    for (step in 0 until matrix.size) {
        result.add(matrix[y, x])

        val direction = trajectory[directionIndex % trajectory.size]
        x += direction.offsetByX
        y += direction.offsetByY

        if (++traveledDistance == distance.toInt()) {
            directionIndex++
            distance += 0.5f
            traveledDistance = 0
        }
    }

    return result
}

private fun generateIntMatrix(size: Int): Matrix<Int> {
    return matrixOf(size, size) { row, column -> row * size + column + 1 }
}

private enum class Direction(val offsetByX: Int, val offsetByY: Int) {
    LEFT(-1, 0),
    TOP(0, -1),
    RIGHT(1, 0),
    BOTTOM(0, 1)
}
