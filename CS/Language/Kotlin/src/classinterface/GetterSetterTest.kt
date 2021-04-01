package classinterface

import java.util.*

/**
 * # 变量、只读变量、编译期常量
 * * Kotlin 声明变量的语法:
 * ```kotlin
 *      var/val <propertyName>[: PropertyType] [= <property_initializer>]
 *          [<getter>]
 *          [<setter>]
 * ```
 * * 变量类型可推断，可不显示声明变量类型
 * * var -> variable, val -> value
 * * 如果没有初始值，就不能自动推断，必须显示声明变量类型
 * * setter 和 getter 针对类成员而言的，不是局部变量
 * * **`val` 和 `var` 的区别：**
 *      * `val` 只能赋值一次，`var` 可以多次赋值
 *      * 在 Kotlin 中 `val` 和 `val` 最本质的区别是 `val` 只有
 *        `getter` 没有 `setter`.
 *
 * * 重写 setter 和 getter 需要注意的
 * * **在重写 getter 或者 setter 中，必须使用 `field` 来隐式访问当前变量，不能直接
 *     使用变量名访问，因为这样回触发循环引用的问题，最终 `java.lang.StackOverflowError`**
 *
 * # 类型推断和动态类型
 *  * **类型推断：声明变量时，已经对变量进行赋值了（可以是直接赋值也可以是函数调用）
 *              可以省略变量类型的声明，因为编译器已经可以根据所给定的变量值或者函数
 *              的返回值类型推断出变量类型，并在编译期由编译器自动添加变量类型。**
 *  * **动态类型：在运行时可以动态的修改变量的类型。**
 *      * 例如：JavaScript、Groovy
 */



/**
 * Kotlin 允许在顶层定义变量和常量, 顶层变量允许使用
 *  `private` 和 `public` 两种权限修饰符；`private`
 *  权限仅限当前 kt 文件内方法；最终是 Kt 文件包装类的
 *  `private static final` 类型的变量. 而 `public`
 *  可以在任何地方访问，是 `public static final` 类型
 *  的变量。可以反编译为 Java 查看
 *      * 定义真正的常量 - 编译时常量
 *      * `const val x = 0`
 */
private const val INDEX_1 = 1 // 编译期常量
public const val INDEX_2 = 2  // 编译期常量


/**
 * 声明常量
 *      * 在 kt 文件声明顶层(top-level)常量，使用 `const val`
 *      * const 变量必须在编译期确定其值，所有 const 只能用来声明
 *         基本数据类型和 String 类型
 *
 * const 关键字只能用来修饰顶层属性，或修饰 object 中的常量
 */

// const 的用法一：修饰顶层常量
const val NUMBER = 5  // 正确
const val KEY = "KEY" // 正确
// const val USER = User("张三") 错误，非 String 的引用类型在编译期无法确定其值

// const 的用法二：修饰 object 中的常量
object Manger {
    const val X = 5
}
// const 的用法三：修饰伴生对象中的常量
class Test {
    companion object {
        const val X = 5
    }
}


class Person(var name: String) {
    //var x: Int // 错误: Kotlin 的类成员没有默认值。要么定义就初始化，要么延迟初始化
    // 正确的 getter/setter 重写
    var age: Int = 0
        set(value) {
            println("Age>>>: 调用了 setter")
            field = value
        }
        get() {
            println("age>>>: 调用了 getter")
            return field
        }

    // 错误的 getter/setter 重写
    var gender: String = "女"
        get() {
            println("调用了 Gender 的 getter")
            return gender
            // 这是直接使用 gender
            // 最终编译后变成了 getGender() 的无穷递归，最终导致堆栈溢出
        }

    // 编译为 java 后
    //   public final String getGender() {
    //      String var1 = "调用了 Gender 的 getter";
    //      System.out.println(var1);
    //      return this.getGender();
    //   }

    val number: Int // 这是一个虚假的常量
        get() {
            // 虽然这是一个只能赋值一次的变量，但 Kotlin 对它的访问是通过
            // getter 方法访问的；只能赋值一次我就不赋值，我直接重写 getter
            // 返回其他值，绕开原有值就行。
            return Random().nextInt()
        }

    // 看起来是常量，其实是变量，因为每次运行结果都不一样
    val numberX = Random().nextInt()

    // 测试成员方法内部是会调用 setter 或 getter
    // 还是直接访问
    fun test() {
        println(age)
        age = 10086
        println(age)
    }
    // 类的内部是调用 setter 和 getter 来访问的，而不是直接访问的
    //    public final void test() {
    //      int var1 = this.getAge();
    //      boolean var2 = false;
    //      System.out.println(var1);
    //      this.setAge(10086);
    //      var1 = this.getAge();
    //      var2 = false;
    //      System.out.println(var1);
    //   }

    override fun toString(): String {
        return "name = $name"
    }
}

fun main() {
    // val 等价于 final -> 不可变的变量，或者说只能赋值一次的变量
    val x = Person("张三")
    // var 修饰变量
    var y = Person("李四")

    println(x)
    println(y)

    x.name = "王五" // 可以修改 x 的内容，不可变只是引用不可变
    println(x)

    //println(y.gender) 堆栈溢出

    println("=== 类内部会使用 Setter/Getter 访问自己的成员吗？===")
    y.test()
    println("警惕 val 类型的类成员打 Getter 牌！！！")
    println("第一次的 val number = ${y.number}")
    println("第二次的 val number = ${y.number}")
    println("第三次的 val number = ${y.number}")

    println("只能赋值一次的运行时变量")
    println(y.numberX) // 每次运行都不一样
}