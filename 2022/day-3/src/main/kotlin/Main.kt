import Rucksack.Compartments

fun main() {
    partTwo()
}

fun partOne() {
    val lines = readFileAsLines("input.txt")

    println(lines.sumOf { Rucksack(Compartments(it)).compartments.commonItems.sumOf { c -> c.priority } })
}

fun partTwo() {
    val lines = readFileAsLines("input.txt")

    val groups = mutableListOf<Group>()
    for(i in lines.indices step 3) {
        val rucksacks = listOf(lines[i], lines[i + 1], lines[i + 2]).map { Rucksack(Compartments(it)) }
        groups.add(Group(rucksacks[0], rucksacks[1], rucksacks[2]))
    }

    println(groups.sumOf { it.commonItems.sumOf { char -> char.priority } })
}

val Char.priority get(): Int = when (code) {
    in 97..122 -> code - 96
    in 65..90 -> code - 38
    else -> 0
}