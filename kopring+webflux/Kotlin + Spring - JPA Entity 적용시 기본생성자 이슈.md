# kotlin + Spring - JPA Entity 적용시 기본생성자 이슈

스프링 부트 버전 2.6.x 버전 이후의 start.spring.io 에서 생성한 프로젝트에는 이 이슈가 없다. start.spring.io 에서 자동으로 kotlin, spring 사용시 필요한 필수 의존성을 채워준다.<br>

스프링 부트 버전을 올리는 와중에 아래 이슈가 발생할 수 있는데, 이때는 start.spring.io 의 프로젝트 생성 기능에서 스프링부트 버전을 2.7.x 이후의 버전으로 세팅 후에 Explore 버튼을 클릭해서 플러그인 의존성을 어떻게 세팅하는지 확인해보면 된다.<br>

JPA 에서는 강제로 기본 생성자를 생성해야 JPA 를 사용할 수 있다. 이런 이유로 kotlin 에서 Entity 클래스를 아무 생각 없이 작성하면 에러를 만난다.<br>

먼저, 아래의 Entity 클래스가 있다고 해보자.

```kotlin
@Entity
class Book(
	val name: String,
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null
)
```

<br>

위의 코드를 아무 설정 없이 적용하면 아래의 에러를 만난다.

```kotlin
Class 'Book' should have [public, protected] no-arg constructor
```

<br>



# build.gradle에 jpa plugin 추가

build.gradle 에 아래와 같이 kotlin jpa plugin 을 추가해준다.

build.gradle

```kotlin
plugins {
    // ...
	id 'org.jetbrains.kotlin.plugin.jpa' version '1.6.21'
}
```

<br>



# build.gradle에 kotlin reflect 추가

build.gradle

```kotlin
dependencies {
    implementation 'org.jetbrains.kotlin:kotlin-reflect:1.6.21'
}
```

<br>



