fun main() {
    val input = AOCInput.readInput()
    partOne(input).also {
        println("Part 1 solution: $it")
    }
    partTwo(input).also {
        println("Part 2 solution: $it")
    }}

fun partOne(input: List<String>): Int {
    val partMap = readInput(input)
    return partMap.partNumbers.sumOf { it.value.toInt() }
}

fun partTwo(input: List<String>): Int {
    val partMap = readInput(input)
    val partNumbers = partMap.partNumbers

    val gears = partMap.symbols.filter { it.char == '*' }.map { symbol ->
        partNumbers.filter { symbol.getNeighbours().any { pos -> it.contains(pos) } }
    }.filter { it.size == 2 }

    return gears.sumOf { it[0].value.toInt() * it[1].value.toInt() }
}

fun readInput(input: List<String>): PartMap {
    val partMap = PartMap()
    input.forEachIndexed { y, line ->
        var temp = ""
        line.forEachIndexed skip@{ x, character ->
            if (character.isDigit()) {
                temp += character
                return@skip
            }

            if (character.isSymbol() && !character.isReturn()) {
                partMap.symbols.add(Symbol(character, Vector2(x, y)))
            }

            if (temp.isNotEmpty()) {
                partMap.numbers.add(Number(temp, Vector2(x - temp.length, y)))
                temp = ""
            }

            return@skip
        }
    }
    return partMap
}

class PartMap {
    val symbols: MutableList<Symbol> = mutableListOf()
    val numbers: MutableList<Number> = mutableListOf()

    val partNumbers: List<Number> get() = numbers.filter { number ->
        symbols.any { symbol -> symbol.position in number.getNeighbours() }
    }
}

data class Number(
    val value: String,
    val position: Vector2,
) {

    private val endPosition: Vector2 get() {
        return Vector2(position.x + value.length - 1, position.y)
    }

    fun contains(vec: Vector2): Boolean {
        return position.y == vec.y && (position.x <= vec.x) && (vec.x <= endPosition.x)
    }

    fun getNeighbours(): List<Vector2> {
        return listOf(
            Vector2(position.x - 1, position.y),
            Vector2(endPosition.x + 1, position.y),
        ) + (-1..value.length).map {
            Vector2(position.x + it, position.y - 1)
        } + (-1..value.length).map {
            Vector2(position.x + it, position.y + 1)
        }
    }
}

data class Symbol(
    val char: Char,
    val position: Vector2,
) {
    fun getNeighbours(): List<Vector2> {
        return listOf(
            Vector2(position.x - 1, position.y),
            Vector2(position.x + 1, position.y),
        ) + (-1..1).map {
            Vector2(position.x + it, position.y - 1)
        } + (-1..1).map {
            Vector2(position.x + it, position.y + 1)
        }
    }
}

data class Vector2(
    val x: Int,
    val y: Int,
)

fun Char.isSymbol() = this != '.'

fun Char.isReturn() = (this == '\r') || (this == '\n')