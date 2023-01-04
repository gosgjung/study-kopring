package io.gosgjung.study.kotlincoroutine.sachawon.ch01

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {
    val job = launch{
        // 1000 번을 반복(repeat) 한다.
        repeat(1000){ i ->
            // 1000번의 반복동안 생성되는 내부 인덱스 i 를 출력한다.
            println("job: I'm sleeping $i")
            // 0.5초의 딜레이를 가진다.
            delay(500L)
        }
    }

    delay(1300L)
    println("main : I'm tired of waiting!")

//    job.cancel()
    job.join()

    println("main : Now I can quit")
}