package io.gosgjung.study.observable.hot

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.PublishSubject
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import java.util.concurrent.TimeUnit

fun main(args: Array<String>) {
    // (1) 100ms 간격으로 옵저버블 인스턴스를 다시 생성
    val observable = Observable.interval(100, TimeUnit.MILLISECONDS)

    // (2) PublishSubject.create 로 Subject 를 생성
    val subject = PublishSubject.create<Long>()

    // (3) Subject 인스턴스를 사용해 옵저버블 인스턴스의 배출을 구독
    observable.subscribe(subject)

    // (4) Subject 인스턴스를 사용해 Subject 인스턴스에 의한 배출에 접근하기 위해 람다를 사용해 구독
    // 마치 옵저버블처럼 구독을 하고 있다.
    subject.subscribe(
        { println("Received $it") }
    )

    // (5) 1100 밀리초 대기
    // 모든 코드 실행이 완료될 때 까지 runBlocking이 스레드를 차단하면서
    // 호출 스레드 내부에서 코루틴 컨텍스트를 모킹(Mocking, 흉내)한다.
    runBlocking { delay(1100) }
}


