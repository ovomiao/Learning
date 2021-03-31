package collections

fun main() {
    val list: List<String> = listOf("abc", "123")
    val mutableList: MutableList<String> = mutableListOf("ABC", "123")

    // 获取 Java 式
    val x = list.get(1)
    println(x)

    // 推荐做法
    val y = list[1]
    println(y)

    // 在尾部添加元素
    mutableList.add("测试")
    mutableList += "又是一个测试"

    // 在指定的 index 位置添加
    mutableList[2] = "指定位置添加"

    mutableList.removeAt(1) // 解决了 Java Integer 的
    // remove 问题

    // 遍历的同时获取索引
    for ((index, item) in mutableList.withIndex()) {
        println("List[$index] = $item")
    }

}