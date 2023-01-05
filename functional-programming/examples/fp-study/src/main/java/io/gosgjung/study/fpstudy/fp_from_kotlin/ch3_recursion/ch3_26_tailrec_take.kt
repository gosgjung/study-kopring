package io.gosgjung.study.fpstudy.fp_from_kotlin.ch3_recursion


//fun <T> List<T>.head() = first()
//fun <T> List<T>.tail() = drop(1)

tailrec fun take(n: Int, list: List<Int>, acc: List<Int> = listOf())
: List<Int> = when {
    0 >= n -> acc
    list.isEmpty() -> acc
    else -> {
        val takeList = acc + listOf(list.head())
        take(n-1, list.tail(), takeList)
    }
}

fun main(args: Array<String>){
    println(take(3, listOf(5,5,5,5,5)))
}