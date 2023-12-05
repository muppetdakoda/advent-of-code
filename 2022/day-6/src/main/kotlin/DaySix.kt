fun main() {
    val lines = AOCInput.readInput()

    indexOfXCharsInARow(lines[0], 4).also {
        println("Part 1 solution: $it")
    }
    indexOfXCharsInARow(lines[0], 14).also {
        println("Part 2 solution: $it")
    }
}

fun indexOfXCharsInARow(line: String, inARow: Int): Pair<Int, String> {
    val charBuffer = LimitedBuffer<Char>(inARow)
    val markerIndex = run breaking@{
        line.forEachIndexed { i, char ->
            charBuffer.push(char)
            if (charBuffer.items.size == inARow) {
                if (charBuffer.items.distinct() == charBuffer.items) {
                    return@breaking i
                }
            }
        }
        return@breaking -1
    } + 1
    return markerIndex to line.substring(markerIndex - inARow, markerIndex)
}

class LimitedBuffer<T>(
    private val length: Int,
) {

    val items: List<T> get() = _items
    private val _items: MutableList<T> = mutableListOf()

    fun push(item: T) {
        if (_items.size == length) _items.removeFirst()
        _items.add(item)
    }
}
