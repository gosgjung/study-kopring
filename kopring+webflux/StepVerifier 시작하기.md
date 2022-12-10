# StepVerifier 시작하기

리액티브 스트림은 코드가 비동기식이기에 반환된 값이 올바른지 확인하는 방법은 있지만 간단하지는 않다.<br>

단순한 동작만을 테스트해볼때는 Publisher 인터페이스를 사용해 스트림을 게시하고 Subscriber 인터페이스를 구현하고 게시자의 스트림을 수집해서 동작을 확인할 수 있다.<br>

하지만, 이런 코드는 테스트 코드로 사용하기에는 좋지 않다. 특수한 경우만 테스트하는 코드이고, 자주 변하게 되는 일반 코드일 수 있기 때문이다.<br>

<br>

# reactor-test 모듈, StepVerifer 

reactor-test 모듈에서는 StepVerifer 를 제공해준다.<br>

StepVerifer 가 제공하는 연쇄형 API를 활용하면 어떤 종류의 Publisher 라도 스트림 검증을 하는 플로를 만들 수 있다.<br>

StepVerifier 를 이용한 테스트코드를 작성할 때 제일 첫 관문인 StepVerifier.create(Publisher)를 먼저 보자. StepVerifier를 설명하면서, 가장 기초적으로 접하게 되는 expectSubscription, expectNext, expectComplete(), verify() 에 대한 설명도 정리해두었다.<br>

<br>

# StepVerifier::create (Publisher\<T\> source)

`Flux.just("Apple", "Banana")` 라는 플로를 테스트하는 코드다.

```kotlin
@Test
fun test1(){
    StepVerifier
    .create(Flux.just("Apple","Banana"))
    .expectSubscription()   // 첫번째로 내보낸 이벤트는 구독관련 이벤트여야 한다.
    .expectNext("foo")  //  다음에 오는 문자열은 "foo" 여야 하고
    .expectNext("bar")  //  그 다음에 오는 문자열은 "bar" 여야 한다.
    .expectComplete()      //  종료시그널이 존재하는지를 검증한다.
    .verify()               //  검증을 실행하려면 verify() 메서드를 호출해야 한다.
    // verify() 메서드를 호출하면 플로 생성을 구독하게 된다.
    // verify() 메서드는 블로킹 호출이기에 검증 플로가 완료될 때 까지 실행이 차단된다.

}
```

<br>

expectSubscription() 

- 첫 번째로 내보낸 이벤트는 구독 관련 이벤트 여야 한다.
- 이벤트의 첫 시작이 구독관련 이벤트인지 검증한다.

<br>

expectNext()

- 요소를 하나 하나 검증할때 사용한다.
- 또는 expectNextCount(n) 을 통해 원하는 만큼의 요소들을 넘어간 후에 남은 요소들을 하나씩 검증할 때도 사용한다.

<br>

expectComplete()

- 종료 시그널이 존재하는 지 검사한다.

<br>

verify()

- StepVerifier.create(P) 로 생성한 이벤트의 플로를 검증을 시작하게끔 하는 메서드다.
- verify() 메서드를 실행하면 플로 생성을 구독하기 시작한다.
- verify() 메서드는 블로킹 호출이다. 따라서 검증 플로가 완료될때 까지 실행이 차단된다.

<br>

여기서 적은 설명들은 아래에서 정리할 다른 예제들에서도 중복되는데, 기왕이면 나타날 때마다 반복적으로 보면 외워질 것이어서 중복되는게 있으면 그대로 또 복사해서 붙여놓을 예정이다. 

<br>

# StepVerifier::create(P) - verify() 를 호출하지 않을 경우

예를 들면 아래와 같이 verify()를 호출하지 않을 경우를 보자.

```kotlin
@Test
fun test1_1(){
    val l = StepVerifier
        .create(Flux.just("Apple", "Banana"))
        .expectSubscription()   // 첫번째로 내보낸 이벤트는 구독관련 이벤트여야 한다.
        .expectNext("foo")  //  다음에 오는 문자열은 "foo" 여야 하고
        .expectNext("bar")  //  그 다음에 오는 문자열은 "bar" 여야 한다.
        .expectComplete()      //  종료시그널이 존재하는지를 검증한다.

    println("===> ${l} ")  // verify() 를 호출하지 않으면, StepVerifier 가 생성한 하나의 검사식 형태일 뿐이다.
                           // verify() 는 Publisher 를 구독하는 역할을 수행한다 
}
```

<br>

위 코드의 결과는 아래와 같다.

```plain
===> reactor.test.DefaultStepVerifierBuilder$DefaultStepVerifier@72cde7cc
```

<br>

그냥 Verify를 절차적으로 하는 `StepVerifer`  객체만 `DefaultStepVerifierBuilder` 를 이용해 생성해둔 상태이고, 실행은 하지 않은 상태이다.<br>

StepVerifier 객체는 `verify()` 메서드를 호출해야 실제로 expect 구문등을 통해 생성해둔 검사식들이 동작한다.<br>

<br>

# expectNextCount(), expectNext() 혼합해서 사용해보기

이벤에는 expectNextCount(), expectNext() 메서드를 어떻게 사용하는지 보자.

```kotlin
@Test
@DisplayName("expectNextCount(), expectNext() 혼합해 사용해보기")
fun test2_원소의_양이_많을때_원하는_양을_생성하는지_확인해보는_경우(){
    StepVerifier
        .create(Flux.range(0, 100)) // 0 ~ 99 까지의 원소를 만든다.
        .expectSubscription()   // 첫번째로 내보낸 이벤트는 구독 관련 이벤트여야 한다.
        .expectNext(0)      	// 첫번째 요소가 0 인지 검사한다.
        .expectNextCount(97)    // 0 이후로 Publisher 가 그 이후에 97개 요소를 잘 만들었는지 검사
        .expectNext(98)     	// 1 부터 97개의 요소 이후(next) 요소는 98 이다. 97번째 카운트 후 98 요소가 맞는지 검사
        .expectNext(99)     	// 98 이후로는 99요소다. 그 다음요소가 99가 맞는지 검사
        .expectComplete()       //  종료시그널이 존재하는지를 검증한다.
        .verify()               //  검증을 실행하려면 verify() 메서드를 호출해야 한다.
}
```

<br>

expectSubscription() 

- 첫 번째로 내보낸 이벤트는 구독 관련 이벤트 여야 한다.
- 이벤트의 첫 시작이 구독관련 이벤트인지 검증한다.

<br>

expectNext()

- 요소를 하나 하나 검증할때 사용한다.
- 또는 expectNextCount(n) 을 통해 원하는 만큼의 요소들을 넘어간 후에 남은 요소들을 하나씩 검증할 때도 사용한다.

<br>

expectNextCount(97)

- 0 이후로 1부터 시작해서 97개의 요소가 존재하는지 검사.
- 99개의 요소중 1개를 소모했고 그 이후로 97번째는 97 이다. 
- 1부터 97개의 데이터가 존재하므로 검증문의 결과는 true 다.

<br>

expectNext(98), expectNext(99)

- expectNext(98)
  - 1부터 97개의 요소를 검사한 expectNextCount(97) 이후의 요소는 98이다.
  - 97번째 이후 요소가 98이 맞는지 검증한다.
- expectNext(99)
  - 98 이후의 요소는 99 다.
  - expectNext(98) 이후로 expectNext(99)를 하므로 99 가 맞으므로 검증문의 결과는 true 다.

<br>

expectComplete()

- 종료 시그널이 존재하는 지 검사한다.

<br>

verify()

- StepVerifier.create(P) 로 생성한 이벤트의 플로를 검증을 시작하게끔 하는 메서드다.
- verify() 메서드를 실행하면 플로 생성을 구독하기 시작한다.
- verify() 메서드는 블로킹 호출이다. 따라서 검증 플로가 완료될때 까지 실행이 차단된다.

<br>

# expectComplete() 

expectComplete() 를 호출하는 시점에는 요소 순회가 모두 끝난 시점이어야 한다. 만약 요소가 하나라도 남아있다면 에러를 낸다.<br>

<br>

```kotlin
@Test
@DisplayName("요소 순회가 끝나지도 않았는데 expectComplete() 를 호출하면 에러가 나야한다.")
fun test2_1_expectComplete_에__대해_조금_더_자세히_살펴보자(){
    StepVerifier
    .create(Flux.just(1,2,3))
    .expectSubscription()
    .expectNext(1)          // f[0]
    .expectNextCount(1) // f[1]
    .expectComplete()           // 요소순회가 끝났는지 expect 검증문을 실행한다.
    // 하지만 요소 순회가 끝나지 않았다.
    // 따라서 에러 발생.
    .verify()
}
```

<br>

# expectNextMatches()

expectNext()의 경우 .equals() 메서드를 사용해 원소의 값을 비교한다.<br>

expectNextMatches()는 matcher를 이용해 원하는 비교식을 이용해 조금 더 복잡한 비교구문을 진행할 수 있다.<br>

e.g.

```kotlin
@Test
fun test4(){
    StepVerifier
    .create(Flux.just("갤럭시 z폴드4", "갤럭시 탭", "아이폰"))
    .expectNextMatches{it.startsWith("갤럭시")}
    .expectNextMatches{it.contains("탭")}
    .expectNext("아이폰")
    .expectComplete()
    .verify()
}
```





# hamcrest 사용하기 - consumeRecordedWith(), recordWith()

특정 규칙에 따라 요소를 필터링하거나 선택하는 코드를 확인할 때, 모든 항목들이 미리 정해둔 필터링 규칙과 일치하는지 확인할 수 있다면 좋다. Hamcrest는 데이터를 검증할 때 필터링 규칙등을 통해서 조금은 편리하게 리스트와 같은 선형 자료구조를 검증하기 쉽게끔 해주는 Java 라이브러리다.<br>

StepVerifer는 자바 Hamcrest를 함께 사용해서 스트림 데이터를 검증할 수 있도록 StepVerifier API 에서 제공하는 메서드들이 있다.<br>

consumeRecordWith() 는 recordWith()를 먼저 호출한 상태에서만 사용할 수 있다.<br>

consumeRecordWith()는 Publisher가 게시한 모든 원소를 검증할 수 있다.<br>

<br>

만약 멀티 스레드 Publisher 를 사용할 경우 이벤트를 기록할 때 사용하는 컬렉션이 동시 액세스(concurrent)를 지원해야 하기 때문에, .recordWith(ArrayList::new) 대신, .recordWith(ConcurrentLinkedQueue::new) 를 사용하는 것이 좋다. ConcurrentLinkedQueue 는 ArrayList와는 다르게 스레드 세이프하기 때문이다.<br>

e.g.

```kotlin
@Test
fun test3(){
    val fluxPlayers = Flux.just(
        Player("손흥민", 99, "WF"),
    )

    StepVerifier
    .create(fluxPlayers)
    .expectSubscription()
    .recordWith { ArrayList<Player>() }
    .expectNextCount(1)
    .consumeRecordedWith { players -> assertThat( // hamcrest assertThat
        players,
        everyItem(hasProperty("name", equalTo("손흥민"))) // hamcrest
    ) }
    .expectComplete()
    .verify()
}
```

<br>

# assertNext(), consumeNextWith()

assertNext()와 expectNextMatches()의 차이점

- expectNextMatches()는 true/false 를 반환해야 하는 조건문(Predicate)을 인자로 받는다.
- assertNext()는 예외를 발생시키는 Consumer를 허용한다. 그리고 해당 Consumer 에서 발생한 모든 AssertionError 는 결과적으로는 verify() 메서드에 의해 캡처되어 예외를 발생시킨다.

<br>

```kotlin
@Test
fun test5(){
    val fluxPlayers = Flux.just(
        Player("마이클오언", 99, "WF"),
        Player("마이클캐릭", 99, "MF")
    )

    StepVerifier
    .create(fluxPlayers)
    .expectSubscription()
    .assertNext { players -> assertThat( // hamcrest assertThat
        players,
        hasProperty("position", equalTo("MF"))  // hamcrest
    ) }
    .expectComplete()
    .verify()
}
```

<br>

# expectError()

에러가 나는지 여부만을 테스트하기

```kotlin
@Test
fun test6(){
    StepVerifier
    // 참고: <T>를 명시해줘야 한다. 아래 처럼 RuntimeException 타입을 명시해줘야 한다.
    .create(Flux.error<RuntimeException>(RuntimeException("에러발생")))
    .expectError()
    .verify()
}
```

<br>

에러타입도 명시해서 테스트하기

```kotlin
data class NameMustBeUpperCaseException (
    override val message: String,
): RuntimeException (message)

@Test
fun test7(){
    StepVerifier
    .create(Flux.error<NameMustBeUpperCaseException>(NameMustBeUpperCaseException("이름은 대문자여야 합니다.")))
    .expectSubscription()
    .expectError(NameMustBeUpperCaseException::class.java)
    .verify()
}
```

<br>

# 고급 테스트 - 흐름제어, 무한스트림

무한스트림

- 스트림이 Subscriber::onComplete() 메서드를 호출하지 않는 스트림
- e.g. 
  - 오랫동안 종료되지 않는 API 에 대한 타임아웃 테스트 케이스
  - 무한정 대기하게 될 수도 있는 로직에 대한 테스트 케이스

<br>

무한스트림 테스트의 문제점

- 테스트가 프로세스가 종료될 때 까지 블로킹 된다.

<br>

StepVerifier 이 제공하는 무한스트림 봉착시 구독 취소기능 메서드들

StepVerifier 는 테스트가 무한스트림에 봉착했을 때, 몇 가지 기댓값을 만족하면 소스에서 구독을 취소하도록 하는 취소 API 메서드들을 제공한다.

<br>

## thenCancel()

`아이폰 14` 가 나타났을 때 `thenCancel()` 을 호출해서 웹소켓 연결인 `websocketPublisher` 을 해제하거나 구독을 취소한다.<br>

```kotlin
@Test
fun test8(){
    val websocketPublisher = Flux.just("갤럭시 z폴드4", "아이폰 14", "아이패드")

    StepVerifier
    .create(websocketPublisher)
    .expectSubscription()
    .expectNext("갤럭시 z폴드4")
    .expectNext("아이폰 14")
    .thenCancel()
    .verify()
}
```





 



## onComplete()



## thenRequest()



## then()



# 가상시간 다루기

















