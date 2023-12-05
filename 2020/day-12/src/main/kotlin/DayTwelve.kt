import kotlin.math.abs

fun main() {
    val input = AOCInput.readInput()
    partOne(input).also {
        println("Part 1 solution: $it")
    }
    partTwo(input).also {
        println("Part 2 solution: $it")
    }
}

fun partOne(input: List<String>): Int {
    val ferry = Ferry()
    input.forEach { instruction ->
        val (_, action, valueString) = Regex("(\\w)(\\d+)").find(instruction)!!.groupValues
        val value = valueString.toInt()

        when(action) {
            "N" -> ferry.move(Direction.NORTH, value)
            "E" -> ferry.move(Direction.EAST, value)
            "S" -> ferry.move(Direction.SOUTH, value)
            "W" -> ferry.move(Direction.WEST, value)
            "L" -> ferry.rotate(-value)
            "R" -> ferry.rotate(value)
            "F" -> ferry.move(ferry.direction, value)
        }
    }

    return abs(ferry.position.x) + abs(ferry.position.y)
}

fun partTwo(input: List<String>): Int {
    val ferry = WaypointFerry()
    input.forEach { instruction ->
        val (_, action, valueString) = Regex("(\\w)(\\d+)").find(instruction)!!.groupValues
        val value = valueString.toInt()

        when(action) {
            "N" -> ferry.moveWaypoint(Direction.NORTH, value)
            "E" -> ferry.moveWaypoint(Direction.EAST, value)
            "S" -> ferry.moveWaypoint(Direction.SOUTH, value)
            "W" -> ferry.moveWaypoint(Direction.WEST, value)
            "L" -> ferry.rotateWaypoint(-value)
            "R" -> ferry.rotateWaypoint(value)
            "F" -> ferry.moveToWaypoint(value)
        }
    }

    return abs(ferry.ferryPosition.x) + abs(ferry.ferryPosition.y)
}

class Ferry {
    var direction: Direction = Direction.EAST
    var position: Vector2 = Vector2(0, 0)

    fun rotate(degrees: Int) {
        direction = Direction.from((direction.degrees + degrees).let {
            if (it < 0) it + 360 else it
        } % 360)
    }

    fun move(direction: Direction, distance: Int) {
        when(direction) {
            Direction.NORTH -> position.y += distance
            Direction.EAST -> position.x += distance
            Direction.SOUTH -> position.y -= distance
            Direction.WEST -> position.x -= distance
        }
    }
}

class WaypointFerry {

    var ferryPosition: Vector2 = Vector2(0, 0)
    private var waypointPosition: Vector2 = Vector2(10, 1)

    fun rotateWaypoint(degrees: Int) {
        if (degrees < 0) {
            repeat((1..degrees / -90).count()) {
                waypointPosition = Vector2(-waypointPosition.y, waypointPosition.x)
            }
        } else if (degrees > 0) {
            repeat((1..degrees / 90).count()) {
                waypointPosition = Vector2(waypointPosition.y, -waypointPosition.x)
            }
        }
    }

    fun moveToWaypoint(times: Int) {
        repeat(times) {
            ferryPosition += waypointPosition
        }
    }

    fun moveWaypoint(direction: Direction, distance: Int) {
        when(direction) {
            Direction.NORTH -> waypointPosition.y += distance
            Direction.EAST -> waypointPosition.x += distance
            Direction.SOUTH -> waypointPosition.y -= distance
            Direction.WEST -> waypointPosition.x -= distance
        }
    }
}

enum class Direction(
    val degrees: Int,
) {
    NORTH(0), EAST(90), SOUTH(180), WEST(270);

    companion object {
        fun from(degrees: Int) = entries.first { it.degrees == degrees }
    }
}

data class Vector2(
    var x: Int,
    var y: Int,
) {
    operator fun plus(vec: Vector2): Vector2 {
        return Vector2(x + vec.x, y + vec.y)
    }
}