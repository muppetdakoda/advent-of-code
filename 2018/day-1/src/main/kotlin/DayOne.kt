fun main() {
    val input = AOCInput.readInput()
    partOne(input)
    partTwo(input)
}

fun partOne(input: List<String>) {
    var frequency = 0
    input.forEach { s ->
        frequency += s.toInt()
    }

    println("Part 1 solution: $frequency")
}

fun partTwo(input: List<String>) {
    val visited = mutableSetOf<Int>()

    var frequency = 0
    var found = false
    do {
        run {
            input.forEach { s ->
                frequency += s.toInt()
                val added = visited.add(frequency)
                if (!added) {
                    found = true
                    return@run
                }
            }
        }
    } while (!found)
    println("Part 2 solution: $frequency")
}