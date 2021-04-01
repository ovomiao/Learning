package other

import util.testRun

/**
 * 求斐波拉契数列
 *      F(0) = 0
 *      F(1) = 1
 *      F(N) = F(N - 1) - F(N - 2)
 */

fun main() {
    testRun {
        println(fib1(50))
    }
    testRun {
        println(fib2(60))
    }
}

/**
 * 【暴力递归】时间复杂的：O(2^n)
 *      * 计算过程：以 f(5) 为例
 *                       f(5)                       1   2^0
 *             f(4)                    f(3)         2   2^1
 *         f(3)   f(2)            f(2)     f(1)     4   2^2
 *     f(2)  f(1)  f(1) f(0)   f(1) f(0)   f(0) f(-1)8  2^3
 *
 *     -> f(N) => 2^N
 *
 *     * 重复计算：f(3): 1, f(2): 2, f(1): 4
 */
fun fib1(n: Int): Int {
    return when {
        n <= 0 -> 0
        n == 1 -> 1
        else -> fib1(n - 1) + fib1(n - 2)
    }
}

/**
 * 假设求 f(5)
 *     * 步骤：
 *      * f(5) = f(4) + f(3)
 *      * f(4) = f(3) + f(2)
 *      * f(3) = f(2) + f(1)
 *      * f(2) = f(1) + f(0)
 *      * 所以，先计算 f(2) 并保存 f(2)
 *      * 计算 f(3) -> 保存
 */
fun fib2(n: Int): Int {
    // 0..n
    val dp = IntArray(n + 1)
    dp[0] = 0
    dp[1] = 1
    // 计算 2..n 的
    for (index in 2..n) {
        // index = 2，dp[1] + dp[0] -> dp[2]
        // index = 3, dp[2] + dp[1] -> dp[3]
        dp[index] = dp[index - 1] + dp[index - 2]
    }
    return dp[n]
}


