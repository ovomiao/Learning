package p1_function_variable

/**
 * object 关键字：
 *      * 用法1： 修饰一个类型，替换 class 关键字为 object
 *          * 作用：定义一个类，并创建这个类的实例；即 Kotlin 语言层面
 *                  支持的单例。
 *          * 访问 object 的方法：
 *              * 类名.方法名()
 *      * 用法2：代替 Java 的匿名内部类
 */

// 单例
object Manager {
    fun show() {
        println("我是单例类中的一个方法")
    }
}

// 匿名内部类对象
fun testObject() {
    Thread(object : Runnable {
        override fun run() {
            println("代替 Java 匿名内部类")
        }
    })
        .start()
}

/**
 * 需要一个非单例类，但需要拥有静态变量和静态函数
 */
class User1 {
    fun name() {
        println("成员函数")
    }

    // 当作这个类的静态属性和方法，但需要注意到是 Kotlin 语言层面本身是没有
    // static 属性和函数的，所有这其实在在 User1 类内部定义了一个对外公开的
    // 内部单例对象，只不过这个对象，在 User1 类对象第一次创建是被创建，在 User1
    // 所有对象被回收时回收，Kotlin 称之为“伴生对象”
    object Holder {
        var defName = "张三"
        fun show() {
            println("静态函数的代替写法")
        }
    }
}
// 上面的写法过于繁琐，Kotlin 提供了一个新的关键字代替它
//  顶层属性和伴生对象的选择
//      * 工具类 -> 顶层属性（顶层函数优先选择）
//      * 类**内部**使用的常量，例如
//          Android 中的 TAG = Activity.class.getName()
//          可以定义到
//          companion object {
//              val TAG = "tag"  // 中
//          }
class User2 {
    fun func() {
        println("成员方法")
    }

    // companion 作用于省略这个 object 的名字
    companion object {
        fun show() {
            println("用于伴生对象方法模仿的静态方法")
        }

        // 为 Java 生成静态变量
        @JvmStatic
        fun staticFunc() {
            println("最终在 JVM 中生成 Static 方法，主要给 Java 用")
        }
    }
}
// object 和 companion object 可以有父类和接口
class User3 {}

// 常量的定义 const val 只有这一种用法
//      * 要求时编译器常量，所以只能时基本数据类型和 String
//      * 在编译时，编译器会将这些常量硬编码到使用处，类似 C 的宏替换
fun main() {
    Manager.show()

    val user1 = User1()
    user1.name()
    println(User1.Holder.defName)
    User1.Holder.show()

    val user2 = User2()
    user2.func()
    User2.show()
}