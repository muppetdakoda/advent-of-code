fun main() {
    val input = AOCInput.readInput()
    partOne(input).also {
        println("Part 1 solution: $it")
    }
    partTwo(input).also {
        println("Part 2 solution: $it")
    }
}

fun partOne(input: List<String>): Long {
    val almanacInput = input.toMutableList()
    val seeds = almanacInput.removeFirst().let {
        it.split("seeds: ")[1].split(" ").map { it.toLong() }
    }

    val almanac = readInput(almanacInput)
    val seedLocations = seeds.map { almanac.getSeedLocation(it) }
    return seedLocations.min()
}

fun partTwo(input: List<String>): Long {
    val almanacInput = input.toMutableList()
    val seeds = almanacInput.removeFirst().let {
        Regex("(\\d+) (\\d+)").findAll(it).toList().map { result ->
            val (_, start, length) = result.groupValues
            start.toLong()..start.toLong() + length.toLong()
        }
    }

    val almanac = readInput(almanacInput)
    val seedLocations = seeds.flatMap { range -> range.map { almanac.getSeedLocation(it) } }
    return seedLocations.min()
}

fun readInput(input: List<String>): Almanac {
    val almanac = Almanac()
    var currentlyMapping = Almanac.RangeMapping.Type.SEED_TO_SOIL

    input.forEach { line ->
        if (line.contains("seed-to-soil")) {
            currentlyMapping = Almanac.RangeMapping.Type.SEED_TO_SOIL
        } else if (line.contains("soil-to-fertilizer")) {
            currentlyMapping = Almanac.RangeMapping.Type.SOIL_TO_FERTILIZER
        } else if (line.contains("fertilizer-to-water")) {
            currentlyMapping = Almanac.RangeMapping.Type.FERTILIZER_TO_WATER
        } else if (line.contains("water-to-light")) {
            currentlyMapping = Almanac.RangeMapping.Type.WATER_TO_LIGHT
        } else if (line.contains("light-to-temperature")) {
            currentlyMapping = Almanac.RangeMapping.Type.LIGHT_TO_TEMPERATURE
        } else if (line.contains("temperature-to-humidity")) {
            currentlyMapping = Almanac.RangeMapping.Type.TEMPERATURE_TO_HUMIDITY
        } else if (line.contains("humidity-to-location")) {
            currentlyMapping = Almanac.RangeMapping.Type.HUMIDITY_TO_LOCATION
        } else if (line.isEmpty()) {
            return@forEach
        } else {
            val (_, destinationStart, sourceStart, length) = Regex("(\\d+) (\\d+) (\\d+)")
                .find(line)!!.groupValues
            if (!almanac.typeMappings.containsKey(currentlyMapping)) {
                almanac.typeMappings[currentlyMapping] = mutableListOf()
            }
            almanac.typeMappings[currentlyMapping]?.add(
                Almanac.RangeMapping(sourceStart.toLong(), destinationStart.toLong(), length.toLong())
            )
        }
    }
    return almanac
}

class Almanac {
    val typeMappings: MutableMap<RangeMapping.Type, MutableList<RangeMapping>> = mutableMapOf()

    fun getSeedLocation(seed: Long): Long {
        var trackValue = seed
        typeMappings.forEach { (_, mappings) ->
            trackValue = mappings.firstOrNull { trackValue in it.source..it.source + it.length }
                ?.let { it.destination + (trackValue - it.source) }
                ?: trackValue
        }
        return trackValue
    }

    class RangeMapping(
        val source: Long,
        val destination: Long,
        val length: Long,
    ) {
        enum class Type {
            SEED_TO_SOIL, SOIL_TO_FERTILIZER, FERTILIZER_TO_WATER,
            WATER_TO_LIGHT, LIGHT_TO_TEMPERATURE, TEMPERATURE_TO_HUMIDITY,
            HUMIDITY_TO_LOCATION
        }
    }
}