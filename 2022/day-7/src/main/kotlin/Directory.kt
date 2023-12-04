open class Directory(
    override val name: String,
) : Item {
    var items: MutableList<Item> = mutableListOf()

    val directories: List<Directory> get() = items.filterIsInstance<Directory>() + (items.filterIsInstance<Directory>().flatMap { it.directories })

    override val size: Int get() = items.sumOf { it.size }

    override fun toString(): String {
        return "Directory(name=$name, size=$size)"
    }
}
