package io.gosgjung.study.fpstudy.fp_from_kotlin.ch3_recursion

fun main(args: Array<String>){
    println(even(9999))     // false
    println(odd(9999))      // true
    println(even(999999))   // java.lang.StackOverflowError
}

fun even(n: Int): Boolean = when (n){
    0 -> true
    else -> odd(n-1)
}

fun odd(n: Int): Boolean = when (n){
    1 -> true
    else -> even(n-1)
}
