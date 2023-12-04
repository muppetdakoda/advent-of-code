fun main() {
    val lines = readFileAsLines("input.txt")

    val elves = mutableListOf(Elf())
    lines.forEach {
        if (it == "") { // make a new Elf
            elves.add(Elf())
        } else { // add calories to current Elf
            elves[elves.size - 1] += it.toInt()
        }
    }
    println("Summed calories for ${elves.size} elves")
    elves.findTop(10)
}

private fun List<Elf>.findTop(i: Int) {
    if (i == 1) {
        val elfWithHighestCalories = maxBy { it.calories }
        val indexOfHighest = indexOf(elfWithHighestCalories)
        println("Elf #${indexOfHighest} has the most calories with ${elfWithHighestCalories.calories}")
    } else if (i > 1) {
        val elvesWithHighestCalories = sortedByDescending { it.calories }.slice(0 until i)
        elvesWithHighestCalories.forEachIndexed { i, elf ->
            println("#${i + 1}\t Elf #${indexOf(elf)}\t ${elf.calories} calories")
        }
    }
}
