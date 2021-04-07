package p1_function_variable

/**
 * Kotlin 变量：
 *      * kotlin 的任何变量都没有默认值，而 Java 只有类成员才有默认值
 *      * Kotlin 中非定义一个变量，在不使用任何延时初始化手段时，必须先
 *      *   先给定初始值，如果不给定就只能将该变量声明为**抽象的**
 */
var iNum: Int = 0

class Variable {
    // 告诉 Kotlin 编译器，我会在使用之前对此变量初始化
    private lateinit var iNum: String
}

/**
 * Kotlin 可空变量的问题
 *      obj?.func()
 *          obj ！= null -> 调用 func()
 *          obj == null -> 不调用
 */
var name: String? = null

fun testNullInt() {
    if (name != null) {
        // 虽然这里判断时，能够保证 name ！= null
        // 但调用时可不一定不为空，因为 name 可能会其他线程改变为 null
        //println(name.length)
        println(name?.length)
    }
}

/**
 * Kotlin 的类型问题：
 *      * 在 Kotlin 语言层面是不存在基本数据类型的，即所有的类型都是引用类型
 *      * 而基于 JVM 平台的 Kotlin 在最终生成的字节码会更具变量所处的环境
 *          * 选择 int 和 Integer 类型
 */

fun main() {

}