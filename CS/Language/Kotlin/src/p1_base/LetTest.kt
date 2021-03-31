package p1_base

/**
 *      * fun <T, R> T.let(block: (T) -> R): R
 *          * let() 函数会将调用者对象传入到 Lambda 中，通常这个函数与
 *              ?. 操作符一起使用。let() 的返回值是 Lambda 最后的值
 *              obj.let { obj1 ->
 *              // 这里的 obj1 和 obj 是同一个对象
 *              }
 */

class User(var name: String, var age: Int) {
    fun funcA() {
        println("funcA>>>:  name = $name, age = $age")
    }

    fun funcB() {

    }

    fun rename(newName: String) {
        println("修改了名字")
        name = newName
    }

    override fun toString(): String {
        return "User = {name: $name, age: $age}"
    }
}

fun testLet(user: User?) {
    // 因为 user 是可空类型的，所有每次调用都要使用
    // if 判空或者使用 ?. 操作符调用。调用一个方法还好
    // 调用多个方法都使用 ?. 就很繁琐了
    //user?.funcA()
    //user?.funcB()
    // 等价
//    if (user != null) {
//        user.funcA()
//    }
//    if (user != null) {
//        user.funcB()
//    }

    /**
     * 说明：?. -> 当 user == null 是什么都不做，当 user ！= null 时，
     *       调用 let() 方法。
     * let() 方法会将调用这传入 Lambda 中。
     * * 具体到这里就是：
     *      当 user ！= null 时，调用 let() 函数，并将 user 对象传入
     *      Lambda 中（默认保存在 it），注意 let() 是有返回值的，即最后一行
     *      的表达式或者函数的返回值。
     */
    val x = user?.let {
        // 因为只有 user != null 才会调用 let()
        // 所以这里的 it 一定是安全的
        it.funcA()
        it.funcB()
        "我是 let 的返回值"
    }
    println(x)
}

fun main() {
    testLet(User("张三", 20))
    testLet(null)
}