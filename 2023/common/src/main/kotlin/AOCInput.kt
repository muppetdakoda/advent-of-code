object AOCInput {

    fun readInput(): List<String> {
        return AOCInput::class.java.getResource("input.txt")?.readText()?.split("\n")
            ?: listOf()
    }
}