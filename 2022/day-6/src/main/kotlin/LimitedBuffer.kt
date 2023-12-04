class LimitedBuffer<T>(
    private val length: Int,
) {

    val items: List<T> get() = _items
    private val _items: MutableList<T> = mutableListOf()

    fun push(item: T) {
        if (_items.size == length) _items.removeFirst()
        _items.add(item)
    }
}