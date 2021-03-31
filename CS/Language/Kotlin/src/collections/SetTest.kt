package collections

fun main() {
    val set: Set<String> = setOf("a", "cb", "142")
    val mutableSet: MutableSet<String> = mutableSetOf("ACC", "BB")

    mutableSet.add("ABC")
    mutableSet += "CDE"
    for (str in mutableSet) {
        println(str)
    }
}