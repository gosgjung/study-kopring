package io.gosgjung.study.observable.hot

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.PublishSubject
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import java.util.concurrent.TimeUnit

fun main(args: Array<String>){
    // (1) 100 밀리초 간격으로 옵저버블 인스턴스를 생성한다.
    val observable = Observable.interval(100, TimeUnit.MILLISECONDS)

    // (2) PublishSubject.create() 로 Subject 를 생성했다.
    val subject = PublishSubject.create<Long>()

    // (3) 옵저버처럼 Subject 인스턴스를 사용해 옵저버블 인스턴스의 배출을 구독
    observable.subscribe(subject)

    // (4) 옵저버블 처럼 Subject 인스턴스를 사용해 Subject 인스턴스에 의한 배출에 접근하기 위해 람다를 사용해 구독
    subject.subscribe(
        { println("Subscription 1 Received $it") }
    )

    // (5)
    runBlocking{
        delay(1100)
    }

    // (6) Subject 를 다시 구독
    // 1100 밀리초 후에 구독을 한다. 따라서 처음 11회의 배출 이후의 데이터를 받는다.
    subject.subscribe(
        { println("Subscription 2 Receive $it") }
    )

    // (7) 다시 프로그램을 1100 밀리초 기다리게 한다.
    runBlocking { delay(1100) }
}
