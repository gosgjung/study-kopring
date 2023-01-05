package io.gosgjung.study.fpstudy.fp_from_kotlin.ch3_recursion.exercise

fun main(args: Array<String>){
    fibo(6)
}

fun fibo(n: Int) : Int {
    println("fibo(${n})")
    return when (n){
        0 -> 0
        1 -> 1
        else -> fibo(n-1) + fibo(n-2)
    }
}