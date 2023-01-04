package io.gosgjung.study.kotlincoroutine.sachawon.ch02_basic

import kotlin.concurrent.thread

fun main(){
    thread{
        Thread.sleep(1000L)
        println("World!")
    }

    println("Hello,")
    Thread.sleep(2000L)
}
