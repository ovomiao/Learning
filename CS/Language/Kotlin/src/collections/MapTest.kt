package collections

fun main() {
    val map = mutableMapOf<String, String>(
        "zhangsan" to "张三"
    )

    println(map["zhangsan"])
    map += "lisi" to "李四" // DSL 式
    map["wangwu"] = "王五"

    for (entry in map) {
        println("${entry.key} = ${entry.value}")
    }

}