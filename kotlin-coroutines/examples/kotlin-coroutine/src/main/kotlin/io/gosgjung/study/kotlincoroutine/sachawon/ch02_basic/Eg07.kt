package io.gosgjung.study.kotlincoroutine.sachawon.ch02_basic

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {
    launch {
        myWorld()
    }
    println("Hello,")
}

suspend fun myWorld(){
    delay(1000L)
    println("World.")
}


