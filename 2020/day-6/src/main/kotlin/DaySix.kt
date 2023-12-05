fun main() {
    val input = AOCInput.readInput()
    partOne(input)
    partTwo(input)
}

fun partOne(input: List<String>) {
    val groups = processGroups(input)
    println("Part 1 solution: ${groups.sumOf { it.unique.size }}")
}

fun partTwo(input: List<String>) {
    val groups = processGroups(input)
    println("Part 2 solution: ${groups.sumOf { it.unanimous.size }}")
}

fun processGroups(input: List<String>): MutableList<Group> {
    val groups = mutableListOf<Group>()
    var group = Group()

    input.forEach {
        if (it == "") {
            groups.add(group)
            group = Group()
            return@forEach
        }
        group.answers.add(Answer(it))
    }
    groups.add(group)
    return groups
}

class Group {
    val answers = mutableListOf<Answer>()

    val unique get(): Set<Char> {
        return answers.joinToString("") { it.answers }.toSet()
    }

    val unanimous get(): Set<Char> {
        var result = answers[0].answers.toSet()
        answers.forEachIndexed { index, answer ->
            if (index == 0) return@forEachIndexed
            result = result.intersect(answer.answers.toSet())
        }
        return result
    }
}

class Answer(val answers: String)