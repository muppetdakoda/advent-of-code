fun main() {
    val lines = readFileAsLines("input.txt")

    val pairs = lines.map { it.split(",") }.map { it[0] to it[1] }.map {
        Assignment(it.first) to Assignment(it.second)
    }

    doOverlaps(pairs)
}

fun doOverlaps(pairs: List<Pair<Assignment, Assignment>>) {
    var numOfOverlaps = 0
    pairs.map {
        it.first.sections.toSet() to it.second.sections.toSet()
    }.forEach { (aOne, aTwo) ->
        if (aOne.intersect(aTwo).isNotEmpty()) {
            numOfOverlaps += 1
        }
    }

    println("There are $numOfOverlaps overlapping assignments")
}

fun doRedundantAssignments(pairs: List<Pair<Assignment, Assignment>>) {
    var numOfRedundantAssignments = 0
    pairs.forEach { (aOne, aTwo) ->
        if (
            aOne.sections.containsAll(aTwo.sections)
            || aTwo.sections.containsAll(aOne.sections)
        ) {
            numOfRedundantAssignments += 1
        }
    }

    println("There are $numOfRedundantAssignments redundant assignments")
}