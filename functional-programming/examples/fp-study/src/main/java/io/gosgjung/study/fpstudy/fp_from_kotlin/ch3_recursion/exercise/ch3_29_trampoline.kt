package io.gosgjung.study.fpstudy.fp_from_kotlin.ch3_recursion.exercise

sealed class Bounce<A>
data class Done<A>(val result: A): Bounce<A>()
data class More<A>(val thunk: () -> Bounce<A>): Bounce<A>()

tailrec fun<A> trampoline(bounce: Bounce<A>): A = when(bounce){
    is Done -> bounce.result
    is More -> trampoline(bounce.thunk())
}

fun odd(n: Int): Bounce<Boolean> = when (n){
    0 -> Done(false)    // 홀수는 0에 도달하면 조건을 불충족
    else -> More{even(n-1)}
}

fun even(n: Int): Bounce<Boolean> = when (n){
    0 -> Done(true)     // 짝수는 0에 도달하면 조건을 충족
    else -> More {odd(n-1)}
}

fun main(args: Array<String>){
    println(trampoline(even(999999)))   // false. java.lang.StackoverflowError 가 발생하지 않는다.
    println(trampoline(odd(999999)))    // true. java.lang.StackoverflowError 가 발생하지 않는다.
}
