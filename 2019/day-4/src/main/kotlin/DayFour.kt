import org.intellij.lang.annotations.Language

fun main() {
    val input = (265275..781584)
    partOne(input)
    partTwo(input)
}

fun partOne(input: IntRange) {
    val matchCriteria = mutableListOf<Int>()
    input.forEach {
        val asString = it.toString()
        if (hasDoubleDigit(asString) && digitsNeverDecrease(asString)) {
            matchCriteria.add(it)
        }
    }
    println("Part 1 solution: ${matchCriteria.size}")
}

fun partTwo(input: IntRange) {
    val matchCriteria = mutableListOf<Int>()
    input.forEach {
        val asString = it.toString()
        if (hasOnlyDoubleDigit(asString) && digitsNeverDecrease(asString)) {
            matchCriteria.add(it)
        }
    }
    println("Part 2 solution: ${matchCriteria.size}")
}

fun hasDoubleDigit(string: String): Boolean {
    return Regex("(.)\\1").containsMatchIn(string)
}

fun hasOnlyDoubleDigit(string: String): Boolean {
    return Regex("((.)\\2+)").findAll(string).any { it.groupValues[0].length == 2 }
}

fun digitsNeverDecrease(string: String): Boolean {
    return string.mapIndexed { i, c ->
        if (i == 0) return@mapIndexed true
        return@mapIndexed c.digitToInt() >= string[i - 1].digitToInt()
    }.all { it }
}