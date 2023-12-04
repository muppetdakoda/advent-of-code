class Elf {
    var calories: Int = 0

    operator fun plusAssign(i: Int) {
        calories += i
    }
}