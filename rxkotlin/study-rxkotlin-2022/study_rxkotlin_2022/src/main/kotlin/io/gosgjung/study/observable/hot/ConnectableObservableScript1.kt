package io.gosgjung.study.observable.hot

import io.reactivex.rxjava3.kotlin.toObservable

fun main(){
    val connectableObservable =
        listOf("str1", "str2", "str3", "str4", "str5")
            .toObservable()
            .publish()

    connectableObservable.subscribe(
        { println("Subscription 1 : ${it}") }
    )

    connectableObservable.map(String::reversed)
        .subscribe(
            { println("Subscription 2 : ${it}") }
        )

    connectableObservable.connect()

    connectableObservable.subscribe(
        {println("Subscription 3 : ${it}")}
    )
}
