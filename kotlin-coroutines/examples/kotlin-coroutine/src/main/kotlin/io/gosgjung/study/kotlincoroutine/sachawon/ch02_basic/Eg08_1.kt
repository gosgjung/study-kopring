package io.gosgjung.study.kotlincoroutine.sachawon.ch02_basic

import kotlinx.coroutines.runBlocking
import kotlin.concurrent.thread

fun main() = runBlocking {
    repeat(100_000){
        thread{
            Thread.sleep(1000L)
            print(".")
        }
    }
}