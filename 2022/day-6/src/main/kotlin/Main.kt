fun main() {
    val lines = readFileAsLines("input.txt")

    val packet = indexOfXCharsInARow(lines[0], 4)
    val message = indexOfXCharsInARow(lines[0], 14)
    println()
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