package io.gosgjung.study.fpstudy.fp_from_kotlin.ch3_recursion

fun String.head() = first()     // first() : 맨 앞의 문자를 리턴
fun String.tail() = drop(1)  // drop() : 맨 앞에서 n 번째 요소까지를 remove 한 문자열을 리턴


// 코틀린으로 배우는 함수형 프로그래밍 67p

fun main(args: Array<String>){
    println("안녕하세요".head()) // 안
    println("안녕하세요".tail()) // 녕하세요
    println("test = ${"요".tail()}")

    println(reverse("안녕하세요"))
}

fun reverse(str: String) : String = when {
    str.isEmpty() -> ""
    else -> reverse(str.tail()) + str.head()
}
