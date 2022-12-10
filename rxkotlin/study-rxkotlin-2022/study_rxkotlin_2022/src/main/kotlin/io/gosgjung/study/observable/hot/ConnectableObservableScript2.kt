package io.gosgjung.study.observable.hot

import io.reactivex.rxjava3.core.Observable
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import java.util.concurrent.TimeUnit

fun main(args: Array<String>){
    val connectableObservable =
        Observable.interval(100, TimeUnit.MILLISECONDS)
            .publish()      // (1)

    // (2)
    connectableObservable.subscribe(
        { println("Subscription 1 : $it") }
    )

    // (3)
    connectableObservable.subscribe(
        { println("Subscription 2 : $it") }
    )

    // (4)
    connectableObservable.connect()

    // (5)
    runBlocking{
        delay(500)
    }

    // (6)
    connectableObservable
        .subscribe({ println("Subscription 3 : ${it}") })

    // (7)
    runBlocking {
        delay(500)
    }
}
