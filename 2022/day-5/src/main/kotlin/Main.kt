fun main() {
    val instructions = readFileAsLines("input.txt")

    val constructInstruction = instructions.subList(0, instructions.indexOf(""))
    val sortInstructions = instructions.subList(instructions.indexOf("") + 1, instructions.size)

    val supplies = constructSupplies(constructInstruction)
    doSorting(supplies, sortInstructions, canMoveMultiple = true)

    supplies.stacks.forEach {
        print(it.peek())
    }
}

fun constructSupplies(instructions: List<String>): Supplies {
    val stacks = mutableListOf<Stack<Crate>>()

    instructions.forEach { line ->
        var stackIndex = 0
        for(i in line.indices step 4) {
            stacks.getOrNull(stackIndex) ?: stacks.add(Stack())
            if (line[i] == '[') {
                stacks[stackIndex].push(Crate(line[i + 1]))
            }
            stackIndex += 1
        }
    }
    return Supplies(stacks.map { it.reversed() })
}

fun doSorting(supplies: Supplies, instructions: List<String>, canMoveMultiple: Boolean = false) {
    val instructionRegex = Regex("move (\\d+) from (\\d+) to (\\d+)")
    instructions.forEach { line ->
        val matchResult = instructionRegex.find(line)?.groupValues!!

        val numberToMove = matchResult[1].toInt()
        val originIndex = matchResult[2].toInt() - 1
        val destinationIndex = matchResult[3].toInt() - 1

        if (canMoveMultiple) {
            val holder = mutableListOf<Crate>()
            for (i in 0 until numberToMove) {
                holder.add(supplies[originIndex].pop())
            }
            holder.reversed().forEach {
                supplies[destinationIndex].push(it)
            }
        } else {
            for (i in 0 until numberToMove) {
                supplies[destinationIndex].push(supplies[originIndex].pop())
            }
        }
    }
}