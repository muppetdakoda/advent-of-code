class Rucksack(
    val compartments: Compartments,
) {

    class Compartments(
        private val one: String,
        private val two: String,
    ) {

        constructor(full: String): this(
            one = full.slice(0 until full.length / 2),
            two = full.slice(full.length / 2 until full.length),
        )

        val both: String get() = one + two
        private val oneArray get() = one.toCharArray()
        private val twoArray get() = two.toCharArray()

        val commonItems: List<Char> = oneArray.intersect(twoArray.toSet()).distinct()
    }
}