val root = Directory("/")
val stack = mutableListOf(root)
fun MutableList<Directory>.push(i: Directory) = add(i)
val MutableList<Directory>.pop get() = removeLast()
val MutableList<Directory>.peek get() = last()

fun main() {
    val lines = readFileAsLines("input.txt").toMutableList()
    lines.removeFirst()

    lines.forEach { instruction ->
        val parts = instruction.split(" ")
        when(parts[0]) {
            "$" -> {
                when(parts[1]) {
                    "cd" -> cd(parts[2])
                    "ls" -> { /* do nothing? */ }
                }
            }
            "dir" -> dir(parts[1])
            else -> file(parts[1], parts[0].toInt())
        }
    }

    val directories = root.directories

    // part one

    val answer = directories.filter { it.size <= 100000 }.sumOf { it.size }
    println(answer)

    // part two

    val spaceLeft = 70_000_000 - root.size
    val need = 30_000_000
    val deleteAtLeast = need - spaceLeft

    println("You should delete ${directories.filter { it.size >= deleteAtLeast }.sortedBy { it.size }[0]} to free space for the update")
}

fun cd(loc: String) {
    when(loc) {
        ".." -> stack.pop
        else -> stack.push(stack.peek.items.find { it is Directory && it.name == loc } as Directory)
    }
}

fun dir(name: String) = stack.peek.items.add(Directory(name = name))

fun file(name: String, size: Int) = stack.peek.items.add(File(name, size))