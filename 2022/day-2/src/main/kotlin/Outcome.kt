import Move.*

enum class Outcome(
    val score: Int,
) {
    WIN(6), LOSE(0), DRAW(3);

    companion object {
        fun fromMoves(player: Move, enemy: Move): Outcome {
            if (player == enemy) return DRAW

            return when (player) {
                ROCK -> if (enemy == SCISSORS) WIN else LOSE
                PAPER -> if (enemy == ROCK) WIN else LOSE
                SCISSORS -> if (enemy == PAPER) WIN else LOSE
            }
        }

        fun fromLetter(c: Char): Outcome = when(c) {
            'X' -> LOSE
            'Y' -> DRAW
            'Z' -> WIN
            else -> throw IllegalArgumentException()
        }
    }
}