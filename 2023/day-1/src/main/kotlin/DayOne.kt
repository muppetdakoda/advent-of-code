fun main() {
    val input = AOCInput.readInput()
    partOne(input).also {
        println("Part 1 solution: $it")
    }
    partTwo(input).also {
        println("Part 2 solution: $it")
    }
}

fun partOne(input: List<String>): Int {
    return input.sumOf { line ->
        val first = line.first { it.isDigit() }
        val last = line.last { it.isDigit() }
        "$first$last".toInt()
    }
}

val words = listOf(
    "one", "two", "three", "four", "five",
    "six", "seven", "eight", "nine"
).mapIndexed { index, s -> s to (index + 1) }.toMap()
val validDigits = words.keys + (1..9).map { it.toString() }

fun partTwo(input: List<String>): Int {
    return input.sumOf { line ->
        val firstIndex = line.indexOfAny(validDigits)
        val lastIndex = line.lastIndexOfAny(validDigits)
        "${readDigit(line, firstIndex)}${readDigit(line, lastIndex)}".toInt()
    }
}

private fun readDigit(line: String, index: Int): Int {
    return if (line[index].isDigit()) {
        line[index].digitToInt()
    } else {
        var word = "${line[index]}"
        var i = index + 1
        while (word !in words.keys) {
            word += line[i++]
        }
        return words[word]!!
    }
}