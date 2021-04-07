import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

fun main(args: Array<String>) {
    println("Hello World!")
    GlobalScope.launch {
        println("=========>>>>>>>>>>>>>>>>")
    }
}