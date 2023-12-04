fun main() {
    val input = AOCInput.readInput()
    partOne(input)
    partTwo(input)
}

fun partOne(input: List<String>) {
    val games = readGames(input)
    games.filter { g ->
        g.showings.all { s -> s.red <= 12 && s.green <= 13 && s.blue <= 14 }
    }.sumOf { it.id }.also {
        println("Part 1 solution: $it")
    }
}

fun partTwo(input: List<String>) {
    val games = readGames(input)
    games.sumOf { g ->
        g.showings.maxOf { it.red } * g.showings.maxOf { it.green } * g.showings.maxOf { it.blue }
    }.also {
        println("Part 2 solution: $it")
    }
}

fun readGames(input: List<String>): List<Game> {
    return input.map { line ->
        Game(
            id = line.split("Game ")[1].split(":")[0].toInt(),
            showings = line.split(": ")[1].split("; ").map { lineShowing ->
                Game.Showing(
                    lineShowing.split(", ").map {
                        with(it.split(" ")) {
                            Game.Showing.Instance(this[0].toInt(), Game.Colour.valueOf(this[1].uppercase()))
                        }
                    }
                )
            }.toMutableList()
        )
    }
}

class Game(
    val id: Int,
    val showings: List<Showing>,
) {

    data class Showing(
        val red: Int,
        val green: Int,
        val blue: Int,
    ) {

        constructor(instances: List<Instance>) : this(
            instances.filter { it.colour == Colour.RED }.sumOf { it.amount },
            instances.filter { it.colour == Colour.GREEN }.sumOf { it.amount },
            instances.filter { it.colour == Colour.BLUE }.sumOf { it.amount },
        )

        data class Instance(
            val amount: Int,
            val colour: Colour,
        )
    }

    enum class Colour {
        RED, GREEN, BLUE
    }
}
