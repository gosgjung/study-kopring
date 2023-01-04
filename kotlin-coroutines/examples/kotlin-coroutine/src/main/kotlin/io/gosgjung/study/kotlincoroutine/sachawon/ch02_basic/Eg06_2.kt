package io.gosgjung.study.kotlincoroutine.sachawon.ch02_basic

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {

    this.launch{
        delay(1000L)
        println("World1")
    }

    this.launch{
        delay(1000L)
        println("World2")
    }

    this.launch{
        delay(1000L)
        println("World3")
    }

    this.launch{
        delay(1000L)
        println("World4")
    }

    this.launch{
        delay(1000L)
        println("World5")
    }

    println("Hello")
}