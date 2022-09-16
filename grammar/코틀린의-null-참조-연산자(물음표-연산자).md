# kotlin null 참조 연산자 (? 연산자)

코틀린의 문법은 [kotlin playground](https://play.kotlinlang.org/) 라는 곳에서 모두 테스트해볼 수 있다.<br>

오늘 정리하는 내용은 코틀린의 `null` 관련 연산자의 편리함에 대해서다. 사실 처음 공부할 때는 이런 걸 잘 못느끼고 조금은 불편하게 느꼈던것 같다.<br>

코틀린에서는 멤버 변수의 초기화를 강제하거나, 아니면 `?` 연산자를 통해 `null` 값이 지정될 수 있음을 명시해줘야 한다.<br>

글이 너무 길다. 역시 그냥 예제로 보는게 편하다.<br>

```kotlin
fun main() {
    var msg : String? = null
    println(msg?.toString())
}
```

<br>

출력결과

```plain
null
```

<br>

위의 코드를 보면 변수 `msg` 를 `String?` 타입으로 선언했다. `String` 타입이지만, `null` 값으로 저장될 수 있음을 의미한다.<br>

신기한 것은 두번째 라인이다.<br>

```kotlin
println(msg.toString())
```

<br>

이렇게 할 때 변수 `msg` 는 현재 `null` 이다. Java 에서 위의 코드를 실행시켰다면, 무조건 `Null Pointer Exception` 이 발생한다. `null.toString()` 을 하려고 했기 때문이다.<br>

하지만 위의 코드를 [kotlin playground](https://play.kotlinlang.org/) 에서 실행시켜보면, `Null Pointer Exception` 없이 잘 실행된다. kotlin 은 `null` 인 객체에 대해 `?.` 연산자를 붙여서 참조할때, 해당 객체가 `null` 일 경우 그 이하의 연산은 더 이상 수행하지 않도록 내부적인 처리가 되어있다.<br>

`Optional` 성애자였던 나는 kotlin `null` 참조 연산자를 보고 `와 진짜 편하네...` 하고 잠깐 멍하니 있었었다.<br>

<br>

## Java 버전으로 변환

개인적으로 생업을 찾아야하는 시기여서... 바쁘다. 아휴...<br>오후 중으로 잠간 시간내어서 바이트 코드로 변환된 것을 정리해볼 예정 ㅠㅠ<br>

<br>