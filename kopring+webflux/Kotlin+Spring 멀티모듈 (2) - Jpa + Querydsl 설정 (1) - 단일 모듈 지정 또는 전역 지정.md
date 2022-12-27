# Jpa + Querydsl 설정 (1) - 단일 모듈 지정 또는 전역 지정



jpa 는 그냥 의존성만 추가해는 작업만 했고, 어렵지 않기에 설명은 생략.  

querydsl 설정은 조금 어렵기에 자세히 설명하기로 결정.<br>

<br>

kotlin 에서 Querydsl 을 사용하려면 kapt 라는 것을 설치해줘야 한다.

kapt 가 뭔지 모른다면, 코틀린에서 사용하는 어노테이션 프로세서 라는 정도로만 알고 있어도 될 듯 하다.<br>

Querydsl 을 설정할 때에는 아래의 절차를 따른다.

- kapt 플러그인 선언 및 apply 선언
- kapt 함수 실행
  - 이때 인자값으로 `org.springframework.boot:spring-boot-configuration-processor` 라는 문자열을 넘겨주면 된다.
  - 무슨이야기인지는 뒤에 예제를 보면 이해가 잘 된다.
- querydsl implementation 추가
- kapt querydsl 추가



# 참고자료

- https://dev-gorany.tistory.com/362

<br>



# 예제 

- querydsl 을 적용하면서 변경된 설정 상의 변경내역 확인은 [여기](https://github.com/gosgjung/study-kopring/commit/c844fb9a5cdd446a26c50a9c1ce51258aec6496f)서 할 수 있다.
- 예제 디렉터리는 [여기](https://github.com/gosgjung/study-kopring/tree/main/kopring%2Bwebflux/examples/kopring-multimodule)에 있다.

<br>



# 1\) 단일 모듈 지정

하위 모듈 하나에만 querydsl 을 적용하는 경우다.

## 루트모듈에 kapt 플러그인 설정

루트모듈의 build.gradle.kts 파일에 아래의 작업들을 해준다.

**plugins**<br>

- 1\) 루트 모듈에 kapt 플러그인을 사용하겠다고 선언
  - `kotlin("kapt") version "1.6.21"`

<br>

**dependencies**<br>

(내 경우는 subprojects 에 해줬다. allproject에 적용하면 루트프로젝트까지도 적용된다.)

- 1\) kapt 플러그인을 사용하겠다 하는 apply 함수를 사용하는 구문을 정의한다.
  - `apply(plugin = "kotlin-kapt")`

- 2\) 추가한 kapt 플러그인에서 제공하는 함수로 spring의 spring-boot-configuration-processor 를 등록해준다.
  - `kapt("org.springframework.boot:spring-boot-configuration-processor")`

- 3\) annotationProcessor 함수로 spring-boot-configuration-processor 를 등록
  - `annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")`


<br>

아래는 변경한 코드.

`build.gradle.kts`

```kotlin
plugins {
    // ...
    
    // kotlin annotation processor ( querydsl 추가시 필요 )
    kotlin("kapt") version "1.6.21"
    // jpa (JPA 기본 생성자 강제 생성 등에 대한 이슈로 인해 kotlin 에서 지원하는 플러그인 적용)
    kotlin("plugin.jpa") version "1.6.21" apply false
    // ...
}

// ... 

// 어노테이션 프로세서 설정 
configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

// ...

subprojects{
    // ...
    apply(plugin = "kotlin-kapt")
    
    dependencies {     
        
        // ...
        
        // 1)
        // annotationProcessor 로 configuration processor 등록
        annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
        // kapt 함수로 spring configuration processor 적용
        kapt("org.springframework.boot:spring-boot-configuration-processor")
    }
}
```

<br>



실제로 적용할 때 1\) 을 빼놓고 어노테이션 프로세서를 등록했을 때 gradle reload 는 잘 되어도 build 는 잘 안되던 이슈가 있었다.<br>





## 하위 모듈에 querydsl 추가

설명은 내일 중으로 정리예정 

`kopring-api/build.gradle.kts`

dependency 에 아래와 같이 추가해준다.

만약, kopring-api 모듈외의 다른 모듈에서 r2dbc-data-jpa 를 쓰면 의존성이 꼬일수 있기에 별도로 지정하는 방식으로 정리해뒀다.

```kotlin
dependencies {
    
    // ...
    
    apply(plugin = "kotlin-jpa")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")

    implementation("com.querydsl:querydsl-jpa:5.0.0")   // !!querydsl
    kapt("com.querydsl:querydsl-apt:5.0.0:jpa")         // !!querydsl
}
```

<br>



# 2) 전역으로 querydsl 설정

전역으로 querydsl 을 지정하면 쉽다. 하지만, 이렇게 하면 멀티 모듈을 쓰는 장점이 그리 크지는 않다고 본다.

루트 프로젝트의 build.gradle 에 아래와 같이 해준다.

```kotlin
plugins {
    // ...
    
    // kotlin annotation processor ( querydsl 추가시 필요 )
    kotlin("kapt") version "1.6.21"
    // jpa (JPA 기본 생성자 강제 생성 등에 대한 이슈로 인해 kotlin 에서 지원하는 플러그인 적용)
    kotlin("plugin.jpa") version "1.6.21" apply false
    // ...
}

// ...

subprojects{
    // ...
    apply(plugin = "kotlin-kapt")
    apply(plugin = "kotlin-jpa")
    
    dependencies {     
        
        // ...
        
        // kapt 함수로 spring configuration processor 적용
        kapt("org.springframework.boot:spring-boot-configuration-processor")
        
        // data-jpa
        implementation("org.springframework.boot:spring-boot-starter-data-jpa")

        // querydsl
        implementation("com.querydsl:querydsl-jpa:5.0.0")   // !!querydsl
        // querydsl 은 어노테이션 프로세서를 사용...
        kapt("com.querydsl:querydsl-apt:5.0.0:jpa")         // !!querydsl
        annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
    }
}
```

