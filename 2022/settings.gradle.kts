rootProject.name = "advent-of-code-2022"

val days = (1..25).map { "day-$it" }.toTypedArray()

include("common", *days)