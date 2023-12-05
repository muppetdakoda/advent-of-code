import java.lang.Exception
import kotlin.Float.Companion.POSITIVE_INFINITY
import kotlin.math.min
import kotlin.system.measureTimeMillis

fun main() {
    val nodes = Nodes(convertInput(AOCInput.readInput()))

    measureTimeMillis {
        partOne(nodes)
    }.also { println("Took ${it}ms") }

    measureTimeMillis {
        partTwo(nodes)
    }.also { println("Took ${it}ms") }
}

fun convertInput(input: List<String>): List<List<Node>> {
    return input.mapIndexed { y, line ->
        line.mapIndexed { x, char ->
            Node(char, x + 1, y + 1, when(char) {
                'S' -> 'a'.code
                'E' -> 'z'.code
                else -> char.code
            })
        }
    }
}

fun partOne(nodes: Nodes) {
    val startingNode = nodes.find { it.char == 'S' }
    val endingNode = nodes.find { it.char == 'E' }
    if (startingNode == null || endingNode == null) {
        throw IllegalArgumentException("Invalid starting and/or ending nodes")
    }
    val result = dijkstra(nodes, startingNode, endingNode)
    println("Part 1 solution (fewest # of steps to end): ${result?.endInfo?.distance}")
}

fun partTwo(nodes: Nodes) {
    val startingNode = nodes.find { it.char == 'E' }
        ?: throw IllegalArgumentException("Invalid starting and/or ending nodes")
    val result = dijkstra(nodes, startingNode)
    println("Part 2 solution (fewest # of steps to end from any 'a'): ${result?.endInfo?.distance}")
}

fun dijkstra(nodes: Nodes, start: Node, end: Node): Result? {
    val nodeInfo: MutableMap<Node, NodeInfo> = nodes.map.flatten().associateWith { NodeInfo() }.toMutableMap()
    fun Node.info() = nodeInfo[this]!!
    fun unvisited() = nodeInfo.filter { !it.value.visited }

    var current: Node = start.also { it.info().distance = 0 }

    while (!end.info().visited) {
        val neighbours = nodes.getNeighbours(current) { it, node ->
            !(it.x == node.x && it.y == node.y) and (it.z <= node.z + 1) and (!it.info().visited)
        }
        neighbours.forEach { neighbour ->
            neighbour.info().distance = min(neighbour.info().distance, current.info().distance + 1)
        }
        current.info().visited = true
        if (end.info().visited) break

        try {
            current = unvisited().minBy { it.key.info().distance }.key
        } catch (_: Exception) {
            return null
        }
    }

    return Result(nodes, start.info(), end.info())
}

fun dijkstra(nodes: Nodes, start: Node): Result? {
    val nodeInfo: MutableMap<Node, NodeInfo> = nodes.map.flatten().associateWith { NodeInfo() }.toMutableMap()
    fun Node.info() = nodeInfo[this]!!
    fun unvisited() = nodeInfo.filter { !it.value.visited }

    var current: Node = start.also { it.info().distance = 0 }

    while (unvisited().minOf { it.value.distance } != POSITIVE_INFINITY.toInt()) {
        val neighbours = nodes.getNeighbours(current) { it, node ->
            !(it.x == node.x && it.y == node.y) and (it.z >= node.z - 1) and (!it.info().visited)
        }
        neighbours.forEach { neighbour ->
            neighbour.info().distance = min(
                neighbour.info().distance,
                if (current.info().distance == POSITIVE_INFINITY.toInt())
                    POSITIVE_INFINITY.toInt()
                else current.info().distance + 1)
        }
        current.info().visited = true
        if (unvisited().minOf { it.value.distance } == POSITIVE_INFINITY.toInt()) {
            break
        }

        try {
            current = unvisited().minBy { it.key.info().distance }.key
        } catch (_: Exception) {
            return null
        }
    }

    val end = nodeInfo.filter { it.key.char == 'a' }.minBy { it.value.distance }
    var curr = end.key
    val path = mutableListOf(curr)
    repeat(end.key.info().distance) { _ ->
        path.add(nodes.getNeighbours(curr) { c, n -> c.info().distance == n.info().distance - 1 }[0].also {
            curr = it
        })
    }
    return Result(nodes, start.info(), end.value)
}

data class Result(
    val nodes: Nodes,
    val startInfo: NodeInfo,
    val endInfo: NodeInfo,
)

data class Nodes(
    val map: List<List<Node>>,
) {
    private fun at(x: Int, y: Int): Node? = try { map[y - 1][x - 1] } catch(_: Exception) { null }

    fun find(predicate: (Node) -> Boolean): Node? {
        map.forEach { row ->
            row.forEach { node ->
                if (predicate(node)) return node
            }
        }
        return null
    }

    fun getNeighbours(node: Node, fValidNeighbour: (Node, Node) -> Boolean): List<Node> {
        return listOfNotNull(
            at(node.x - 1, node.y),
            at(node.x + 1, node.y),
            at(node.x, node.y - 1),
            at(node.x, node.y + 1),
        ).filter { fValidNeighbour(it, node) }
    }
}

data class Node(
    val char: Char,
    val x: Int,
    val y: Int,
    val z: Int,
)

data class NodeInfo(
    var distance: Int = POSITIVE_INFINITY.toInt(),
    var visited: Boolean = false,
)
