fun main() {
    val input = listOf(
        Record(53, 333),
        Record(83, 1635),
        Record(72, 1289),
        Record(88, 1532),
    )
    partOne(input).also {
        println("Part 1 solution: $it")
    }
    val input2 = Record(53837288, 333163512891532)
    partTwo(input2).also {
        println("Part 2 solution: $it")
    }
}

fun partOne(input: List<Record>): Long {
    return input.map { record ->
        (1..record.time).map {
            val timeToMove = record.time - it
            val speed = it
            speed * timeToMove
        }.count { it > record.distance }
    }.reduce { acc, x -> acc * x }.toLong()
}

fun partTwo(input: Record) = partOne(listOf(input))

data class Record(
    val time: Long,
    val distance: Long,
)