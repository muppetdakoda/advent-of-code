class Stack<T> constructor(
    private var _items: MutableList<T> = mutableListOf(),
) {

    fun push(item: T) = _items.add(item)
    fun pop(): T = _items.removeLast()
    fun peek(): T = _items.last()
    fun reversed() = this.apply { _items = _items.reversed().toMutableList() }
}