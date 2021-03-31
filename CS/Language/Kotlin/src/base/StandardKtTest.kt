package base

/**
 *      * fun <T, R> with(receiver: T, block: T.() -> R): R
 *          * 功能说明：
 *              * 一般用于简化对一个对象的方法或属性多次访问。
 *                  * 例：val x = User()
 *                       x.setName(xxx)
 *                       x.setAge(i)
 *                       x.change(xx)
 *                   // 需要多写很多次 x 变量，使用 with，就可以
 *                   // 不用写 x，每次直接 setName(xxx),xxx
 *                   就行在这类中调用或访问自己方法或属性一样。就这么回事
 *
 *          * 参数1：任意对象
 *          * 参数2：Lambda 表达式。这个 Lambda 表达式提供一个对象上下文，
 *                  并将 Lambda 表达式中最后一行作为整个 with() 函数的返回值
 *                  返回。
 *      * fun <T, R> T.run(block: T.() -> R): R
 *          * 功能和 with() 方法一致，返回值也是 Lambda 最后一行的返回值，
 *            只不过 run() 方法是基于某个对象调用。而 with() 是直接调用
 *            看源码就知道了。
 *          * 即
 *              * with()
 *                  * with(obj) {
 *                      // this -> obj
 *                    }
 *              * run()
 *                  * obj.run {
 *                      // this -> obj
 *                  }
 *      * apply
 */

val list = listOf("abc", "def", "你好")

fun testWith1() {

    val str: String = with(StringBuilder()) {
        // 这里的 this 指向 with 的第一个参数对象，即 StringBuilder()
        this.append("test") // 使用 this
        for (str in list) {
            append(str) // 直接简化，去掉 this
        }
        toString()
    }
    println(str)
}

fun testWith2(user: User) {
    val usr: User = with(user) {
        funcA()
        funcB()
        rename("不是只有链式调用才能用这个！")
        this // 返回操作后的对象
    }

    println(usr)
}

fun testRun() {
    val str = StringBuilder().run {
        append("这是 run()")
        for (item in list) {
            append(item)
        }
        toString()
    }
    println(str)
}

fun testApply() {
    val str: String = StringBuilder().apply {
        for (item in list) {
            append(item)
        }
    }.toString() // 因为 apply() 方法是返回调用者对象的，所以这里实际上是调用了
    // StringBuilder 的 toString()
    println(str)
}

fun main() {
    testWith1()
    testWith2(User("李四", 55))
    testRun()

    testApply()

    /**
     * with()、run()、apply() 调用用来简化
     * 多次操作同一个对象的。
     *  * 调用的区别
     *      with() 是直接调用
     *      run() 和 apply() 是基于某个对象调用
     *  * 返回值的区别
     *      with() 和 run() 是返回 Lambda 最后一行的值
     *      apply() 是返回调用者本身
     *
     *  * 利用这三个函数可以简化很多调用，例如 Android 中给 Intent 添加 Extra 时
     *  可以这样
     *  val intent = Intent(this, Activity::class.java).apply {
     *      putExtra("key", "value")
     *      ...
     *      putExtra("key", "value")
     *  }
     */
}

