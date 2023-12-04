@JvmInline
value class Crate(
    private val char: Char,
) {
    override fun toString(): String = char.toString()
}