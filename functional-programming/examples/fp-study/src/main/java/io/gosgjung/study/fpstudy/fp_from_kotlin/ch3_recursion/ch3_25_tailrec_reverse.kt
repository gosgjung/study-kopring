package io.gosgjung.study.fpstudy.fp_from_kotlin.ch3_recursion

//fun String.head() = first()     // first() : 맨 앞의 문자를 리턴
//fun String.tail() = drop(1)  // drop() : 맨 앞에서 n 번째 요소까지를 remove 한 문자열을 리턴

fun reverse1(str: String): String = when{
    str.isEmpty() -> ""
    else -> reverse1(str.tail()) + str.head()
}

tailrec fun reverse2(str: String, acc: String = ""): String = when{
    str.isEmpty() -> acc
    else -> {
        val reversed = str.head() + acc
        reverse2(str.tail(), reversed)
    }
}

fun main(args: Array<String>){
    println(reverse2("안녕하세요"))
}