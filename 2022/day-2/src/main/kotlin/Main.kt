fun main() {
    partTwo()
}

fun partOne() {
    val lines = readFileAsLines("input.txt")

    var playerScore = 0
    lines.forEach {
        val chars = it.split(" ").map { s -> s.single() }

        val enemyLetter = chars[0]
        val playerLetter = chars[1]

        playerScore += Match(
            playerMove = Move.fromLetter(playerLetter),
            enemyMove = Move.fromLetter(enemyLetter),
        ).playerScore
    }

    println("Player achieved score of $playerScore over all matches")
}

fun partTwo() {
    val lines = readFileAsLines("input.txt")

    var playerScore = 0
    lines.forEach {
        val chars = it.split(" ").map { s -> s.single() }

        val enemyLetter = chars[0]
        val outcomeLetter = chars[1]

        val enemyMove = Move.fromLetter(enemyLetter)

        playerScore += Match(
            playerMove = Move.forOutcome(enemyMove, Outcome.fromLetter(outcomeLetter)),
            enemyMove = enemyMove,
        ).playerScore
    }

    println("Player achieved score of $playerScore over all matches")
}