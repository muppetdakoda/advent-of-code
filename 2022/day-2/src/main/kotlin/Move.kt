import Outcome.*

enum class Move(
    val score: Int,
) {
    ROCK(score = 1),
    PAPER(score = 2),
    SCISSORS(score = 3);

    companion object {
        fun fromLetter(c: Char): Move = when(c) {
            'A', 'X' -> ROCK
            'B', 'Y' -> PAPER
            'C', 'Z' -> SCISSORS
            else -> throw IllegalArgumentException()
        }

        fun forOutcome(move: Move, outcome: Outcome): Move = when(outcome) {
            DRAW -> move
            WIN -> when(move) {
                ROCK -> PAPER
                PAPER -> SCISSORS
                SCISSORS -> ROCK
            }
            LOSE -> when(move) {
                ROCK -> SCISSORS
                PAPER -> ROCK
                SCISSORS -> PAPER
            }
        }
    }
}