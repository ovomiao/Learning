package collections

val list = listOf("List", "Map", "Set", "HashMap")

fun getMaxStr(list: List<String>): String? {
    val iterator = list.iterator()
    if (!iterator.hasNext()) {
        return null
    }
    var maxStr = iterator.next();
    if (!iterator.hasNext()) {
        return maxStr
    }
    while (iterator.hasNext()) {
        val curStr = iterator.next()
        // 内部实际上就是把这一步参数化了，因为目标元素不一定是 String
        // 比较规则也不一定是字符串的长度
        if (maxStr.length < curStr.length) {
            maxStr = curStr
        }
    }
    return maxStr
}

/**
 * * maxByOr -> 函数：按规则查找集合中的最大值
 */
fun testMaxBy() {
    val str = list.maxByOrNull {
        println("查找最长字符串中") //这里可以干一些其他的事
        it.length // 只要这个 Lambda 法回过来规则即可
    }
    // 等价逻辑
    val myMaxByResult = getMaxStr(list)
    println("MyMaxBy: $myMaxByResult")

    println("List 中最长的元素是：$str")
}

/**
 * sortedBy 对集合排序，并返回一个新的集合
 */
fun sortTest() {
    val newList = list.sortedBy {
        it.length
    }
    println("原集合：$list")
    println("排序后的新合：$newList")
}

fun mapTest() {
    val newMap1 = list.map {
        it.toUpperCase()
    }
    println("MapTest>>>: $newMap1")
    // 多种变换一起执行
    val newMap2 = list.map {
        // 截取子串
        var str = it.substring(0..2)
        // 转换为大写
        str.toUpperCase() // 这里记得返回操作后的结果
    }
    println("MapTest >>>: $newMap2")
}

fun main() {
    testMaxBy()
    sortTest()
    mapTest()
}