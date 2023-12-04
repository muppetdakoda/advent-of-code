class Supplies(
    val stacks: List<Stack<Crate>> = listOf()
) {
    operator fun get(i: Int) = stacks[i]
}