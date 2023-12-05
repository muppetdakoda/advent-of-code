import Passport.Field.BIRTH_YEAR
import Passport.Field.COUNTRY_ID
import Passport.Field.EXPIRATION_YEAR
import Passport.Field.EYE_COLOUR
import Passport.Field.HAIR_COLOUR
import Passport.Field.HEIGHT
import Passport.Field.ISSUE_YEAR
import Passport.Field.PASSPORT_ID

fun main() {
    val input = AOCInput.readInput()
    partOne(input).also {
        println("Part 1 solution: $it")
    }
    partTwo(input).also {
        println("Part 1 solution: $it")
    }
}

val REGEX_INFO = Regex("(\\w+):([^\\n\\r ]+)")

fun partOne(input: List<String>): Int {
    fun passportIsValid(passport: Passport): Boolean {
        return Passport.Field.entries
            .filter { it != Passport.Field.COUNTRY_ID }
            .all { it in passport.info.keys }
    }

    val passports = readInput(input)
    return passports.count(::passportIsValid)
}

fun partTwo(input: List<String>): Int {
    fun passportIsValid(passport: Passport): Boolean = with(passport.info) {
        try {
            this[BIRTH_YEAR]?.toInt() in (1920..2002)
                    && this[ISSUE_YEAR]!!.toInt() in (2010..2020)
                    && this[EXPIRATION_YEAR]!!.toInt() in (2020..2030)
                    && this[HEIGHT]!!.let {
                        val (_, num, unit) = Regex("(\\d+)(cm|in)").find(it)!!.groupValues
                        if (unit == "cm") return@let num.toInt() in (150..193)
                        if (unit == "in") return@let num.toInt() in (59..76)
                        false
                    }
                    && this[HAIR_COLOUR]!!.matches(Regex("#[0-9a-f]{6}"))
                    && this[EYE_COLOUR]!! in listOf("amb", "blu", "brn", "gry", "grn", "hzl", "oth")
                    && this[PASSPORT_ID]!!.matches(Regex("[0-9]{9}"))
        } catch (e: NullPointerException) {
            false
        }
    }

    val passports = readInput(input)
    return passports.count(::passportIsValid)
}

fun readInput(input: List<String>): MutableList<Passport> {
    val passports = mutableListOf(Passport())
    input.forEach { line ->
        if (line.isEmpty()) passports.add(Passport()).also { return@forEach }
        REGEX_INFO.findAll(line).forEach {
            val (_, fieldShort, value) = it.groupValues
            passports.last().info[Passport.Field.from(fieldShort)] = value
        }
    }
    return passports
}

class Passport {

    val info: MutableMap<Field, String> = mutableMapOf()

    enum class Field(
        private val short: String,
    ) {
        BIRTH_YEAR("byr"), ISSUE_YEAR("iyr"), EXPIRATION_YEAR("eyr"),
        HEIGHT("hgt"), HAIR_COLOUR("hcl"), EYE_COLOUR("ecl"),
        PASSPORT_ID("pid"), COUNTRY_ID("cid");

        companion object {
            fun from(short: String) = entries.first { it.short == short }
        }
    }
}