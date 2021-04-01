package collections

val list = listOf("List", "Map", "Set", "HashMap", "ArrayList")

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

/**
 * 变换数据，甚至可以改变数据类型
 * 返回一个新的集合 List<R>
 */
fun mapTest() {
    val newMap1 = list.map {
        it.toUpperCase() // 转换为大写
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
    val newMap3 = list.map {
        // 直接取出原集合的长度作为变换后的元素
        it.length
    }
    println("newMap3 >>>: $newMap3")
}

/**
 * 过滤元素：
 *      * 将所有符合 Lambda 表达式的值（即 Lambda 最后一行的值），添加到新的 List
 *        中。即过滤条件为 false 的元素，保留 true
 *      * 说是过滤其实也是查找（即需要 findAll 效果可以用这个，注意目前是没有 findAll 操作符的）
 */
fun filterTest() {
    val results = list.filter {
        it.length <= 4
    }
    println("所有 <= 4 的元素：$results")
}

/**
 * find
 *  * 按 Lambda 表达式返回的布尔值，第一个符合条件的元素
 * findLast
 *  * 按 Lambda 表达式返回的布尔值，最后一个符合条件的元素
 */
fun findTest() {
    val result = list.find {
        it.length <= 4 // 找到一个字符串长度 <= 4 的元素
    }
    println("第一种长度 <= 4 的元素为：$result")
}

fun main() {
    testMaxBy()
    sortTest()
    mapTest()

    filterTest()
    findTest()
}