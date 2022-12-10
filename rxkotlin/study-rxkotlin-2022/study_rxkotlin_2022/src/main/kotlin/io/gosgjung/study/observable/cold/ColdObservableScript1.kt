package io.gosgjung.study.observable.cold

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.kotlin.toObservable


fun main(){
    val observable: Observable<String> =
        listOf("str1", "str2", "str3", "str4")
            .toObservable()

    // onNext, onError, onComplete
    observable.subscribe(
        { println("Received $it") },        // onNext
        { println("Error ${it.message}") }, // onError
        { println("Done") }                 // onComplete
    )

    // onNext, onError, onComplete
    observable.subscribe(
        { println("Received $it") },        // onNext
        { println("Error ${it.message}") }, // onError
        { println("Done") }                 // onComplete
    )
}