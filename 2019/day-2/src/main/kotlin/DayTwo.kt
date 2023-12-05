fun main() {
    val input = AOCInput.readInput()[0].split(",").map { it.toInt() }
    partOne(input)
    partTwo(input)
}

fun partOne(input: List<Int>) {
    val results = process(input, 12, 2)
    println("Part 1 solution: ${results[0]}")
}

fun partTwo(input: List<Int>) {
    for (noun in 0..99) {
        for (verb in 0..99) {
            val results = process(input, noun, verb)
            val answer = results[0]
            if (answer == 19690720) {
                println("Part 2 solution: ${(100 * noun) + verb}")
                return
            }
        }
    }
}

fun process(data: List<Int>, inputOne: Int, inputTwo: Int): MutableList<Int> {
    val sequence = data.toMutableList()
    sequence[1] = inputOne
    sequence[2] = inputTwo

    var pointer = 0
    var isRunning = true
    do {
        val opcode = Opcode.from(sequence[pointer])
        val numOne = sequence[sequence[pointer + 1]]
        val numTwo = sequence[sequence[pointer + 2]]
        val numThree = sequence[pointer + 3]

        when(opcode) {
            Opcode.ADD -> sequence[numThree] = numOne + numTwo
            Opcode.MULTIPLY -> sequence[numThree] = numOne * numTwo
            Opcode.END -> isRunning = false
        }

        pointer += 4
    } while (isRunning)
    return sequence
}

enum class Opcode(val value: Int) {
    ADD(1), MULTIPLY(2), END(99);

    companion object {
        fun from(value: Int) = entries.first { it.value == value }
    }
}