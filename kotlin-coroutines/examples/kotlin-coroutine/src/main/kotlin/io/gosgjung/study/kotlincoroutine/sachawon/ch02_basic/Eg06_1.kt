package io.gosgjung.study.kotlincoroutine.sachawon.ch02_basic

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {
    val job1 = GlobalScope.launch {
        delay(1000L)
        println("World!")
    }

    val job2 = GlobalScope.launch {
        delay(1000L)
        println("World!")
    }

    println("Hello,")
    job1.join()
    job2.join()
}
