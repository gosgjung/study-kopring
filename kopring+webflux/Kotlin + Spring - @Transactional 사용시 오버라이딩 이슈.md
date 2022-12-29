# 코틀린 스프링 - @Transactional 사용시 오버라이딩 이슈

최신 버전의 스프링 또는 스프링 부트라면 이것이 적용되지 않을수도 있을 듯 하다.

스프링 부트의 경우 2.7.7 버전으로 프로젝트를 생성하면 자동으로 인텔리제이에서 스프링을 지원해주기위해 제공하는 plugin 들을 붙여준다.<br>

<br>

코틀린에서 스프링을 사용할 때 스프링의 트랜잭션 기능을 사용할 때 아래와 같은 에러를 접한다.

```plain
Methods annotated with '@Transactional' must be overridable
Make 'saveUser' 'open', Make 'UserService' 'open'
```

<br>

이 경우 아래와 같이 open 키워드를 붙여줘서 해결할 수도 있다.

```kotlin
@Service
// open 키워드를 붙였다.
open class UserService(
    private val userRepository: UserRepository,
){
    // open 키워드를 붙였다.
    @Transactional
    open fun saveUser(request: UserCreateRequest){
        
    }
}
```



@Transactional 을 사용하면, 스프링에서는 내부적으로 @Transactional 을 적용한 클래스에 대한 트랜잭션 프록시 객체를 생성하고 해당 메서드를 overriding 한다.<br>

그런데 코틀린에서는 클래스를 상속받을 수 있게 하려면 `open` 이라는 키워드를 사용해야 한다.

<br>

이런 이유로 위의 코드 처럼 `open` 키워드를 붙인 것이다. 하지만 위의 방법은 새로운 라이브러리를 수정하기 위해 애플리케이션 계층의 코드를 수정했다. 조금은 불편한 방식이다.<br>

이런 이유로 jetbrains 측에서는 아래의 플러그인을 제공해준 듯 하다. 언제부터 지원됐는지는 모르겠당...(아직 코린이 ㅠㅠ)<br>

아래의 plugins 항목을 build.gradle 내에 포함시켜주면, 위의 이슈는 해결 된다.<br>

<br>

```kotlin
plugins {
    id "org.jetbrains.kotlin.plugin.spring" version "1.6.21"
}
```

아마도 내부적으로는 @Transactional 어노테이션에 대해 뭔가 리플렉션으로 접근해서 필요한 기능들을 지원해주지 않았을까 싶다. 스프링 팀과 젯브레인스에서의 빠른 지원해준 것에 감사해야겠군<br>

<br>

# 결론

결론!!! 이 중요하다.

@Transactional 관련해서 overriding 관련 등 이런 에러가 나오면 아래의 두 해결책 중 하나를 생각할 수 있다.

- @Transactional 이 붙은 모든 클래스, 모든 메서드에 `open` 을 붙여준다.
- plugin 중에 `org.jetbrains.kotlin.plugin.spring` 을 사용한다.

<br>

그리고 사내 프로젝트에 도입할 수 있을 정도의 스프링 부트 버전이라면, 가급적 `org.jetbrains.kotlin.plugin.spring` plugin 을 하용하는 것이 가장 좋은 방법이다.<br>

<br>