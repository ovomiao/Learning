package util

fun format(time: Long) = when {
    time >= 1000 -> "执行时间：${(time * 1.0f)/1000} s"
    else -> "执行时间：$time ms"
}

fun testRun(func: () -> Unit) {
    val startTime = System.currentTimeMillis()
    println("========================================================")
    func()
    val time = System.currentTimeMillis() - startTime
    println(format(time))
    println("========================================================")
}