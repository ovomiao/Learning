package base

/**
 * 区间
 */
fun main() {
    /**
     * 闭区间
     */
    val range1 = 0..10 // [0, 10]
    println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>")
    for (item in range1) {
        println(item)
    }
    println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<")

    /**
     * 前闭后开区间
     */
    val range2 = 0 until 10 //[0,10)
    println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>")
    for (item in range2) {
        println(item)
    }
    println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<")

    /**
     * 降区间
     */
    val range3 = 10 downTo 0 // [10, 0]
    println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>")
    for (item in range3) {
        println(item)
    }
    println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<")

    /**
     * 字符区间
     */
    val range4 = 'A'..'Z' // [A, Z]
    println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>")
    for (item in range4) {
        println(item)
    }
    println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<")
}
