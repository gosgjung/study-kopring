# kotlin 에서 Array 타입에 varargs 를 전달하기

예를 들면 아래와 같이 자바에서 사용되는 스프링 부트 애플리케이션을 구동하는 코드가 있다고 해보자.

```java
@SpringBootApplication
public class TestApplication{
    public static void main(String [] args){
        SpringApplication app = new SpringApplication(TestApplication.class);
        app.setWebApplicationType(WebApplicationType.NONE);
        app.run(args);
    }
}
```

<br>

이 코드는 코틀린으로 바꾸면 아래와 같이 변경 가능하다.

```kotlin
package io.study.aop_study 
import org.springframework.boot.SpringApplication 
import org.springframework.boot.WebApplicationType 
import org.springframework.boot.autoconfigure.SpringBootApplication 
import org.springframework.boot.runApplication
@SpringBootApplication 
class AopStudyApplication 
fun main(args: Array<String>) { 
	// 웹 애플리케이션으로 띄우는 방식 
//	runApplication<AopStudyApplication>(*args) 
	// 한번 동작하고 멈추도록 띄우는 방식 
	val app : SpringApplication = SpringApplication(AopStudyApplication::class.java) 
	app.webApplicationType = WebApplicationType.NONE 
	app.run(*args) 
}
```

<br>

`args` 라는 varargs 는 `*args` 로 표현해서 앞에 `*` 을 붙여서 `Array` 타입으로 변환해서 전달 가능하다.<br>

<br>

오늘의 잠깐 코틀린 상식 정리 끝!!!<br>

<br>

