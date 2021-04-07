package p2_lambda

/**
 * 高阶函数（Higher-Order Function）：
 *      * 函数形参或者返回值是一个函数的函数
 *  * 函数类型的参数，需要注意的：
 *      * 函数类型的参数是指一类类型，而不是一个
 *      * 一个函数类型的参数和函数类型
 *          * 需要确定此函数类型的参数是什么
 *              * 多少形参，每个形参的类型
 *          * 这个函数类型它自己的返回值类型是什么
 *              * 注意：Unit 类型返回值，在这里是不可以省略的
 *
 */

fun higher1(param: (Int, Int) -> Int) {
    // 接收一个 fun(a: Int, b: Int): Int 类型的函数作为参数
}

//fun higher2(): (Int, Int) -> Int {
//    // 返回值是一个函数的
//}

val funcx = ::higher1 // 将函数赋值给一个变量

/**
 * :: -> 函数引用，可以理解为 C 里面的一个指向函数的指针，它的引用会一个函数
 *      （类型的对象）而不会触发函数调用。
 *
 *   * 这里要注意：函数就是函数类型，它不是一个对象，所以函数本身是无法作为一个形参
 *              或者返回值传递的。
 *              * 在 C/C++ 中如果需要传递一个函数类型的参数，我们就需要定义一个
 *                函数指针记录函数的首地址，最好将这个函数指针传入。
 *              * 而 Kotlin 实际上是使用了一个类去包裹这需要作为参数的函数，而这个
 *                类的功能和函数的功能一致，最近创建这个类的对象，将它传递函数，或作为
 *                函数的返回值。
 *              * 所以 ::函数名 -> 获取指向目标函数的引用
 *                  * 函数类型的调用
 *                      * val a = ::b
 *                          * 语法糖调用：a()
 *                          * 本质：a.invoke()
 *
 * () -> 调用函数
 */

fun a(str: Int): String {
    return str.toString()
}

val b = ::a // 将 a 函数所对应的对象赋值给 b，此时 b 是一个对象
val c = b   // b 已经是一个对象了，所以直接赋值给 c 即可

// 接收一个 Int 形参，返回 string 类型的高阶函数
fun higher3(func: (Int) -> String) {
    println(func.invoke(0))
}

fun main() {
    c(2) // 语法糖调用
    c.invoke(32) // 语法糖的内部逻辑

    higher3(::a)

    // 匿名函数：没有名字的函数
    higher3(fun(str: Int): String {
        return str.toString()
    })

    // lambda 表达式
    higher3 {
        it.toString()
    }

    // SAM 接口问题：
    //      Java 的 SAM 接口允许使用匿名函数
    // Kotlin 的 SAM 接口，在 Kotlin 1.4 之前不允许使用匿名函数或者 Lambda
    // 因为 Kotlin 希望你使用高阶函数实现。

    // 匿名函数、::函数名、Lambda 都是函数类型的对象
    // Lambda 是可传递可执行的一个代码块，但 Kotlin 的 Lambda 也是使用
    // 函数类型对象实现的
}
