fun main() {
    val input = AOCInput.readInput()
    partOne(input).also {
        println("Part 1 solution: $it")
    }
    partTwo(input).also {
        println("Part 2 solution: $it")
    }
}

fun partOne(input: List<String>): Long {
    val allFish = readInput(input).let { all ->
        mapOf(*(0..8).map { it to all.count { n -> n == it }.toLong() }.toTypedArray())
    }.toMutableMap()
    repeat(80) {
        tick(allFish)
    }
    return allFish.values.sum()
}

fun partTwo(input: List<String>): Long {
    val allFish = readInput(input).let { all ->
        mapOf(*(0..8).map { it to all.count { n -> n == it }.toLong() }.toTypedArray())
    }.toMutableMap()
    repeat(256) {
        tick(allFish)
    }
    return allFish.values.sum()
}

const val RESET_AGE = 6
const val NEW_FISH_AGE = 8

fun tick(allFish: MutableMap<Int, Long>) {
    val atZero = allFish[0]!!
    (0..8).forEach {
        when (it) {
            RESET_AGE -> allFish[it] = allFish[it + 1]!! + atZero
            NEW_FISH_AGE -> allFish[it] = atZero
            else -> allFish[it] = allFish[it + 1]!!
        }
    }
}

fun readInput(input: List<String>): List<Int> {
    return input.flatMap { line ->
        line.split(",").map { it.toInt() }
    }
}