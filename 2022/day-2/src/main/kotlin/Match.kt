class Match(
    private val enemyMove: Move,
    private val playerMove: Move,
) {

    val playerScore get(): Int {
        val outcome = Outcome.fromMoves(playerMove, enemyMove)
        return outcome.score + playerMove.score
    }
}