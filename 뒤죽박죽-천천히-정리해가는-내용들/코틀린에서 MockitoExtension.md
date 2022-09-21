# 코틀린에서 MockitoExtension

ㅋㅋ 한참 찾았다...<br>

그냥 일단 정리해놔야겠다는 생각이 들었다.<br>

이제부터 아무거나 생각나는 대로 코틀린에 대해 찾아본 모든 것들을 막 던져놓기로 마음먹었다.<br>

<br>

이렇게 밤샐수 있는 때가 언제 다시 올까...<br>

지금 배울수 있을때 배워둬야겠다.<br>

<br>

MockitoExtension 을 추가하는 상황이었는데, java 를 사용할 때처럼 아래와 같은 어노테이션을 코틀린 파일에 적용하면 컴파일 에러가 나타났다.

```kotlin
@ExtendWith(MockitoExtension.class)
class SomeTest{
    
}
```

<br>

그래서 이것 저것 찾아보다보니 아래의 자료를 찾았다.

- [How to best use Mockito in Kotlin?](https://discuss.kotlinlang.org/t/how-to-best-use-mockito-in-kotlin/24675)
- 검색어는 kotlin MockitoExtension 이었다.
- 쓰잘데 없는 말을 두 줄 적어버렸다...

<br>

코틀린에서는 아래와 같이 MockitoExtension 클래스를 @ExtendWith 하면 된다.

```kotlin
package io.kakao.pay_data_collector.payment.api.priceapicontroller

import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class)
class PostPaymentTest {
}
```

<br>

코틀린에서는 

`@ExtendWith(MockitoExtension::class)` 와 같이 입력해주면 된다.<br>

<br>