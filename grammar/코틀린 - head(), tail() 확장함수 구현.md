# 코틀린 - head(), tail() 확장함수 구현

별 내용은 아니지만, 엄청 중요한 내용까지는 아니지만 메모를 해두기로 결정.<br>

list.head(), list.first() 는 함수형 프로그래밍에서 제공하는 재귀 문제를 풀다보면 굉장히 자주 나오는 관용구였다.<br>

문자열을 다룰 때도 str.head(), str.tail() 를 자주 사용한다.<br>

<br>

head(), tail() 는 사용자가 직접 구현해야 한다. <br>

구현할 때 코틀린에서 각 자료형에서 제공하는 `first()`, `drop(n)` 메서드를 통해 재정의 후 문제를 풀면 된다.<br>

이 first(), drop(n) 은 코틀린에서 각 자료형 마다 코틀린에서 제공하는 `_Strings.kt` , `_Collections.kt`  내에 정의되어 있는 확장함수다.<br>

<br>



# 참고자료

- [Kotlin List tail function](https://stackoverflow.com/questions/35808022/kotlin-list-tail-function)

<br>



# 참고

first() 함수

- 코틀린의 kotlin.collections 패키지 내에 존재하는 확장 함수다.
- 참고 : [https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/first.html](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/first.html)

<br>

drop(n) 함수

- 코틀린의 kotlin.collections 패키지 내에 존재하는 확장 함수다.
- 참고 : [https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/drop.html](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/drop.html)

<br>



# 해결방법

head() 함수 정의 

```kotlin
fun <T> List <T>.head() = first()
```

<br>

tail() 함수 정의

```kotlin
fun <T> List <T>.tail() = drop(1)
```

<br>

e.g. take(n) 함수를 재귀를 이용해 구현

```kotlin
// 확장함수 정의
fun <T> List<T>.head() = first()
fun <T> List<T>.tail() = drop(1)

fun main(args: Array<String>){
    println(take(3, listOf(1,2,3,4,5)))
}

fun take(n: Int, list: List<Int>): List<Int> = when {
    n <= 0 -> listOf()
    list.isEmpty() -> listOf()
    else -> listOf(list.head()) + take(n - 1, list.tail())
}
```





