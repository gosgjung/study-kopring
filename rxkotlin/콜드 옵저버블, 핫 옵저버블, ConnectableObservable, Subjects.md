# 콜드 옵저버블, 핫 옵저버블, ConnectableObservable 객체, Subjects

Observables 는 아래와 같이 핫 옵저버블, 콜드 옵저버블 두 가지가 있다.

- 핫 옵저버블
- 콜드 옵저버블

<br>

# 콜드 옵저버블

각 구독마다 처음부터 아이템을 배출하는 것을 콜드 옵저버블이라고 한다.<br>

콜드 옵저버블은 구독 시에 실행을 시작한다.<br>

subscribe가 호출되면 아이템을 푸시하기 시작한다.<br>

콜드 옵저버블은 각 아이템의 동일한 순서를 지켜서 푸시한다.<br>

데이터의 순서가 뒤바뀌지 않고 동일한 순서를 지켜서 푸시된다.<br>

콜드 옵저버블은 수동적이며, 구독이 호출될 때 까지 아무것도 내보내지 않는다.<br>

콜드 옵저버블은 푸시를 반복하고 각 구독마다 따로 푸시를 보낸다.<br>

<br>

예를 들어 안드로이드에서 SQLite나 Room 데이터베이스로 작업하는 동안은 핫 옵저버블보다는 콜드 옵저버블에 더욱 많이 의존한다.<br>

<br>

> 내 경우는 이렇게 이해했다. `콜드` 라는 단어처럼 얼음처럼 한 덩이씩 뭉치로 전송한다는 것으로 이해했다. 다만, 여러 개가 모일때까지 더미로 보내기에, 핫 퍼블리셔보다는 느리겠다 싶었다.<br>

<br>

e.g. `ColdObservableScript1.kt`

```kotlin
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
```

<br>

# 핫 옵저버블

콜드 옵저버블은 수동적이며, 구독이 호출될 때까지 아무것도 내보내지 않는다.<br>

핫 옵저버블은 콜드 옵저버블과는 반대로 배출을 시작하기 위해 구독할 필요가 없다.<br>

<br>

핫 옵저버블은 데이터보다는 이벤트와 유사하다. 이벤트에는 데이터가 포함될 수 있지만 시간에 민감한 특징을 가지는데 최근 가입한 Observer가 이전에 내보낸 데이터를 놓칠 수 있기 때문이다.<br>

이런 특징은 안드로이드/자바FX/스윙(Swing) 등에서 UI 이벤트를 다룰 때 유용하다. 또는 서버 요청을 흉내내는데에 유용하다.<br>

<br>

핫 옵저버블에는 아래의 두 종류의 객체들이 있다.

- ConnectableObservable
  - 가장 유용한 핫 옵저버블 중의 하나
  - 옵저버블,콜드 옵저버블을 핫 옵저버블로 바꿀수 있다.
  - `subscribe` 호출로 배출을 시작하는 대신 `connect` 메서드를 호출한 후에 활성화된다.
- Subjects

<br>



## ConnectableObservable

가장 유용한 핫 옵저버블 중의 하나다.<br>

옵저버블,콜드 옵저버블을 핫 옵저버블로 바꿀수 있다.<br>

`subscribe` 호출로 배출을 시작하는 대신 `connect` 메서드를 호출한 후에 활성화된다.<br>

ConnectableObservable 객체에 대해 여러 곳에서 subscribe 를 할수 있으며, ConnectableObservable 객체의 connect() 함수를 호출하면 연결된 모든 구독들에 대해 배출이 시작된다. 이렇게 여러개의 구독을 하는 관찰자들에게 배출을 하는 것을 멀티캐스팅이라고 한다.<br>

`connect` 메서드가 호출된 이후에 subscribe를 호출하면 배출을 수신하지 못하게 된다.

<br>

ConnectableObservable 의 주요 목적은 하나의 옵저버블에 여러 개의 구독을 연결해 하나의 푸시에 대응할 수 있도록 하는 것이다.<br>

- 예를 들면 ConnectableObservable 객체 `con1` 에 대해 subscribe는 여러 곳에서 구독을 하게끔 선언할 수 있다.
- 그리고 이렇게 구독한 subscribe는 `con1.connect()` 를 수행하면, 구독한 모든 subscribe 들에 대해 배출을 시작한다.

<br>

**멀티캐스팅**<br>

이렇게 단 한번의 배출로 모든 구독(Subscriptions)/관찰자(Observers)에게 배출을 전달하는 메커니즘을 멀티캐스팅이라고 한다.<br>

<br>

**콜드 옵저버블과의 비교**<br>

콜드 옵저버블의 경우는 푸시를 반복하고 각 구독마다 따로 푸시를 보낸다. 하지만 핫 옵저버블인 ConnectableObservable은 하나의 옵저버블에 여러개의 구독을 연결해서 하나의 푸시에 대응할 수 있다.<br>

ConnectableObservable 은 connect 메서드 이전에 호출된 모든 subscriptions(Observers)를 연결하고 모든 옵저버에 단일 푸시를 전달한다. 그러면 옵저버는 해당 푸시에 대응한다.<br>

<br>

e.g. `ConnectableObservableScript1.kt`

```kotlin
fun main(){
    // (1) 
    val connectableObservable =
        listOf("str1", "str2", "str3", "str4", "str5")
            .toObservable()
            .publish()

    // (2)
    connectableObservable.subscribe(
        { println("Subscription 1 : ${it}") }
    )

    // (3)
    connectableObservable.map(String::reversed)
        .subscribe( // (4)
            { println("Subscription 2 : ${it}") }
        )

    // (5)
    connectableObservable.connect()

    // (6)
    connectableObservable.subscribe(
        {println("Subscription 3 : ${it}")}
    )
}
```

<br>

(1) 

- toObservable() 메서드로 옵저버블을 생성했다.
- publish() 메서드로 콜드 옵저버블을 ConnectableObservable 로 변환했다.
  (Observable은 콜드 옵저버블이다. 따라서 `Observable` 을 `ConnectableObservable` 로 변환한 것이다.)

(2)

- connectableObservable 을 구독한다.

(3)

- 문자열을 뒤집은 요소들을 구독한다.

(4)

-  map에서 뒤집은 문자열 요소들의 connectableObservable을 구독한다.

(5)

- connectableObservable 을 connect()를 통해 subscribe() 한 모든 구독에 대해 배출을 시작한다.

(6)

- 배출을 받지 못한다. 

<br>

출력결과

```plain
Subscription 1 : str1
Subscription 2 : 1rts
Subscription 1 : str2
Subscription 2 : 2rts
Subscription 1 : str3
Subscription 2 : 3rts
Subscription 1 : str4
Subscription 2 : 4rts
Subscription 1 : str5
Subscription 2 : 5rts
```

<br>

이번에는 조금 다른 예제다.

e.g.

```kotlin
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
```

<br>

(1)

- 콜드 옵저버블을 ConnectableObservable 로 변환했다.

(2), (3), (4)

- 그리고 구번의 구독을 수행한 후 connect() 를 호출했다.

(5)

- connect() 를 호출 후 즉시 지연을 호출했다.

(6)

- (5) 에서 즉시 지연을 호출한 후 (6)에서는 다시 구독을 하고 있다.

(7)

- 3번째 구독이 이 일부 데이터를 인쇄할 수 있도록 다시 지연을 호출한다.

<br>

출력결과

Subscription 3 이 5번째 배출을 받았고, 이전의 구독을 모두 놓쳤다.

```plain
Subscription 1 : 0
Subscription 2 : 0
Subscription 1 : 1
Subscription 2 : 1
Subscription 1 : 2
Subscription 2 : 2
Subscription 1 : 3
Subscription 2 : 3
Subscription 1 : 4
Subscription 2 : 4
Subscription 1 : 5
Subscription 2 : 5
Subscription 3 : 5
Subscription 1 : 6
Subscription 2 : 6
Subscription 3 : 6
Subscription 1 : 7
Subscription 2 : 7
Subscription 3 : 7
Subscription 1 : 8
Subscription 2 : 8
Subscription 3 : 8
Subscription 1 : 9
Subscription 2 : 9
Subscription 3 : 9
```

<br>

## Subjects

Hot Observables 의 한 종류다. 이것 역시 Hot Observales 를 구현하는 좋은 방법 중 하나.

기본적으로 옵저버블과 옵저버의 조합이다. 그리고 옵저버블, 옵저버의 공통된 동작을 모두 갖고 있다는 것은 장점

핫 옵저버블 처럼 내부에 Observer 목록을 유지하고, 배출 시에 가입한 모든 옵저버에게 단일 푸시를 전달한다.

<br>

Subject 가 제공하는 것은 아래와 같다. 아래와 같이 옵저버블과 옵저버의 기능들을 모두 가지고 있다. Subject는 옵저버와 옵저버블이 조합되어 있다.

- 옵저버블이 가져야 하는 모든 연산자를 가지고 있다.
- 옵저버와 마찬가지로 배출된 모든 값에 접근할 수 있다.
- Subject가 완료(completed), 오류(errored), 구독 해지(unsubscribed) 된 후에는 재사용할 수 없다.
- 가장 흥미로운 점은 그 자체로 가치를 전달한다는 것이다.
  - 자세히 설명하면, onNext 를 사용해 값을 Subject(Observer) 측에 전달하면 Observable에서 접근 가능하게 된다.

<br>

**e.g. SubjectsScript1.kt**

Subject 인스턴스는 옵저버블 인스턴스에 의한 배출에 귀를 기울이고 있다가 그 배출을 자신들의 Observers에게 브로드캐스팅한다.

```kotlin
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
```

<br>

출력결과

```plain
Received 0
Received 1
Received 2
Received 3
Received 4
Received 5
Received 6
Received 7
Received 8
Received 9
Received 10
```

<br>

**e.g. SubjectsScript2.kt**

```kotlin
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
```



출력결과

```plain
Subscription 1 Received 0
Subscription 1 Received 1
Subscription 1 Received 2
Subscription 1 Received 3
Subscription 1 Received 4
Subscription 1 Received 5
Subscription 1 Received 6
Subscription 1 Received 7
Subscription 1 Received 8
Subscription 1 Received 9
Subscription 1 Received 10
Subscription 1 Received 11
Subscription 2 Receive 11
Subscription 1 Received 12
Subscription 2 Receive 12
Subscription 1 Received 13
Subscription 2 Receive 13
Subscription 1 Received 14
Subscription 2 Receive 14
Subscription 1 Received 15
Subscription 2 Receive 15
Subscription 1 Received 16
Subscription 2 Receive 16
Subscription 1 Received 17
Subscription 2 Receive 17
Subscription 1 Received 18
Subscription 2 Receive 18
Subscription 1 Received 19
Subscription 2 Receive 19
Subscription 1 Received 20
Subscription 2 Receive 20
Subscription 1 Received 21
Subscription 2 Receive 21

Process finished with exit code 0

```

<br>

# 다양한 구독자

구독자들 중 중요하게 다뤄지는 구독자들은 아래와 같다.

- AsyncSubject
- PublishSubject

<br>

이 외에도 아래의 구독자들도 있다.

- BehaviorSubject
- ReplaySubject

<br>







