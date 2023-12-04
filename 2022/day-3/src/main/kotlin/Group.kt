class Group(
    private val one: Rucksack,
    private val two: Rucksack,
    private val three: Rucksack,
) {
    val commonItems get(): List<Char> {
        return one.compartments.both.toCharArray().intersect(
            two.compartments.both.toCharArray().toSet()
        ).intersect(
            three.compartments.both.toCharArray().toSet()
        ).toList()
    }
}