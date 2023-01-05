package io.gosgjung.study.fpstudy.fp_from_kotlin.ch3_recursion.exercise.solution

fun main(){
    println(toBinary(10))
    println(toBinary(27))
    println(toBinary(255))
}

private tailrec fun toBinary(n: Int, acc: String = ""): String = when {
    n < 2 -> n.toString() + acc
    else -> {
        val binary = (n % 2).toString() + acc
        toBinary(n / 2, binary)
    }
}