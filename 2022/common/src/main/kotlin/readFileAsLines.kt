import java.io.File

@Suppress("unused")
fun readFileAsLines(resource: String): List<String> {
    val url = object {}.javaClass.getResource(resource)
    val inputStream = File(url!!.path).inputStream()
    val list = mutableListOf<String>()
    inputStream.bufferedReader().forEachLine { list.add(it) }
    return list
}