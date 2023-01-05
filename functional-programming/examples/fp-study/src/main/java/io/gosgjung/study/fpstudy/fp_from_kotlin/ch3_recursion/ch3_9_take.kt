package io.gosgjung.study.fpstudy.fp_from_kotlin.ch3_recursion

/**
 * 입력 리스트에서 입력받은 숫자 만큼의 값만 꺼내오는 take 함수를 만들어보자.
 */

fun <T> List<T>.head() = first()
fun <T> List<T>.tail() = drop(1)

fun main(args: Array<String>){
    println(take(3, listOf(1,2,3,4,5)))
}

fun take(n: Int, list: List<Int>): List<Int> = when {
    n <= 0 -> listOf()
    list.isEmpty() -> listOf()
    else -> listOf(list.head()) + take(n - 1, list.tail())
}

