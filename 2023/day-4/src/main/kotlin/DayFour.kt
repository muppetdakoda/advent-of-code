import kotlin.math.pow

fun main() {
    val input = AOCInput.readInput()

    partOne(input).also {
        println("Part 1 solution: $it")
    }
    partTwo(input).also {
        println("Part 2 solution: $it")
    }
}

val LINE_REGEX = Regex("Card +(\\d+): ([ \\d]+)\\|([ \\d]+)")

fun partOne(input: List<String>): Int {
    val cards = readInput(input)
    return cards.sumOf { 2F.pow(it.numberOfMatches - 1).toInt() }
}

fun partTwo(input: List<String>): Int {
    val cardPiles = readInput(input).associateBy { it.index }.toMutableMap().mapValues { mutableListOf(it.value) }
    while(cardPiles.any { it.value.any { card -> !card.processed } }) {
        cardPiles.forEach { (index, pile) ->
            pile.filter { !it.processed }.forEach { unprocessedCard ->
                unprocessedCard.processed = true
                if (unprocessedCard.numberOfMatches > 0) {
                    (index + 1.. index + unprocessedCard.numberOfMatches).forEach {
                        cardPiles[it]?.get(0)?.let { originalCard -> cardPiles[it]?.add(originalCard.copy(processed = false)) }
                    }
                }
            }
        }
    }
    return cardPiles.entries.sumOf { it.value.size }
}

fun readInput(input: List<String>): List<ScratchCard> {
    return input.map { line ->
        val (_, index, winners, found) = LINE_REGEX.find(line)!!.groupValues
        ScratchCard(index.toInt(), winners.toIntList(), found.toIntList())
    }
}

data class ScratchCard(
    val index: Int,
    val winners: List<Int>,
    val found: List<Int>,
    var processed: Boolean = false,
) {
    val numberOfMatches: Int get() = winners.count { number -> number in found }
}

fun String.toIntList() = this.split(" ").filter { it != "" }.map { it.toInt() }