class Assignment(
    assignment: String,
) {
    val sections: List<Int>

    init {
        sections = with(assignment.split("-").map { it.toInt() }) {
            this[0]..this[1]
        }.toList()
    }
}