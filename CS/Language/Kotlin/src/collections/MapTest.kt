package collections

fun main() {
    val map = mutableMapOf<String, String>(
        "zhangsan" to "张三"
    )

    // 获取
    println(map["zhangsan"]) // 推荐
    map += "lisi" to "李四" // DSL 式
    map["wangwu"] = "王五" // 推荐

    // 遍历得到 Entry，不是最简方式
    for (entry in map) {
        println("${entry.key} = ${entry.value}")
    }

    for ((key, value) in map) {
        println("$key = $value")
    }

}