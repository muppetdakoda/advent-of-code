fun main() {
    val lines = readFileAsLines("input.txt")

    val grid = makeGrid(lines)

    var numOfVisible = 0
    val scenicScores = mutableListOf<Int>()
    grid.forEachIndexed { y, row ->
        row.forEachIndexed { x, current ->
            if (y == 0 || x == 0) numOfVisible += 1
            else if (y == grid.size - 1 || x == grid[0].size - 1) numOfVisible += 1
            else {
                // part one

                val treesUp = grid.slice(0 until y).map { it[x] }.reversed()
                val treesDown = grid.slice(y + 1 until grid.size).map { it[x] }
                val treesLeft = grid[y].slice(0 until x).reversed()
                val treesRight = grid[y].slice(x + 1 until grid[y].size)

                if (
                    treesUp.none { it >= current }
                    || treesDown.none { it >= current }
                    || treesLeft.none { it >= current }
                    || treesRight.none { it >= current }
                ) {
                    numOfVisible += 1
                }

                // part two

                scenicScores += (
                    treesUp.sliceUntil(0) { it >= current }.size
                    * treesDown.sliceUntil(0) { it >= current }.size
                    * treesLeft.sliceUntil(0) { it >= current }.size
                    * treesRight.sliceUntil(0) { it >= current }.size
                )
            }
        }
    }
    println("$numOfVisible trees visible from outside the grid.")
    println("Highest possible scenic score is ${scenicScores.max()}")
}

fun <T> List<T>.sliceUntil(start: Int, until: (T) -> Boolean): List<T> {
    return slice(start..(indexOfFirst { until(it) }.takeIf { it > -1 } ?: (size - 1)))
}

fun makeGrid(lines: List<String>): Array<IntArray> {
    val grid = Array(lines.size) { IntArray(lines[0].length) }
    lines.forEachIndexed { y, row ->
        row.forEachIndexed { x, char ->
            grid[y][x] = char.digitToInt()
        }
    }
    return grid
}