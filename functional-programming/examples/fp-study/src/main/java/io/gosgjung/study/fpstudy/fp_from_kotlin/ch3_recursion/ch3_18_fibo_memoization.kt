package io.gosgjung.study.fpstudy.fp_from_kotlin.ch3_recursion

var memo = Array(100) { -1 }

fun main(args: Array<String>){
    println(fibo_memo(6))
}

fun fibo_memo(n: Int): Int = when {
    n == 0 -> 0
    n == 1 -> 1
    memo[n] != -1 -> memo[n]
    else -> {
        println("fibo_memo(${n})")
        memo[n] = fibo_memo(n-1) + fibo_memo(n-2)
        memo[n]
    }
}


