# kotlin 에서 Slf4J Logger 객체 생성하기

# 참고자료

- [Kotlin 에서 SLF4J Logger 객체 생성 방법](https://lelecoder.com/156)
- [Spring Boot 에서 Loggint 사용, Log4j2 사용하기](https://www.wool-dev.com/backend-engineering/spring/spring-kotlin-logging-simple)

<br>

## Slf4j

- 로그를 남기고 추적하는 요구사항이 많이 생겨, 이와같은 요구사항들을 해소하고자 Loggin Framework이 생겼다
- 스프링의 Logging Framework 중 가장 유명한 라이브러리는 Slf4j 이다
- Slf4j를 사용하면 `logback`, `log4j`, `log4j2` 와 같은 구현체를 쉽게 교체하고 사용할 수 있다

<br>



# 코틀린에서 Slf4J Logger 사용하기

예제는 아래와 같다.

```kotlin
import org.junit.jupiter.api.Test
import org.slf4j.LoggerFactory

class SomeTest {

    val logger = LoggerFactory.getLogger(javaClass)

    @Test
    fun loggingTest1(){
        logger.info("logging Test 1")
    }
}
```

<br>



# log4j2 설정

- xml 설정방법과 Jackson을 사용한 설정방법 두가지 방식으로 설정 가능
- Springboot의 `resource` 폴더 하위에 `xml`파일을 생성
- 생성한 `xml`파일을 `log4j2.xml`로 저장하고, 아래의 내용을 적어준다

<br>

**build.gradle.kts**

```kotlin
// ...

dependencies {
    // ...
    implementation("org.springframework.boot:spring-boot-starter-log4j2")
    // ... 
}

configurations.forEach {
    it.exclude(group = "org.springframework.boot", module = "spring-boot-starter-logging")
    it.exclude(group = "org.apache.logging.log4j", module = "log4j-to-slf4j")
}

// ...

```

<br>



**log4j2 xml 설정**

- src/main/resources 디렉터리 아래에 `log4j2.xml` 파일 생성

<br>

**log4j2.xml** 

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<Configuration status="INFO">
    <Properties>
        <Property name="LOG_PATTERN">%d{HH:mm:ss.SSSZ} [%t] %-5level %logger{36} - %msg%n</Property>
    </Properties>
    <Appenders>
        <Console name="ConsoleLog" target="SYSTEM_OUT">
            <PatternLayout pattern="${LOG_PATTERN}" charset="UTF-8"/>
        </Console>
    </Appenders>
    <Loggers>
        <Root level="info">
            <AppenderRef ref="ConsoleLog"/>
            <AppenderRef ref="FileLog"/>
        </Root>
    </Loggers>
</Configuration>
```

<br>

- Configuration: 로그 설정을 위한 최상위 요소
  - status 속성: Log4j2 내부의 동작에 대한 로깅 레벨을 설정 (log4j 내부 문제를 해결하기 위한 용도의 로깅이 필요한 경우 사용)
- Properties: 하단 설정에 사용할 변수들을 정의
  - name: 위 예제에서 name=”LOG_PATTERN”으로 설정하여 LOG_PATTERN이라는 변수를 정의
  - Appenders: 로그가 출력되는 위치
  - Console: 콘솔에 출력될 로그 설정
    - name: 어펜더의 이름
    - target: 로그 타겟 (default: SYSTEM_OUT)
  - PatternLayout: 로그의 패턴을 설정
- Loggers: 로깅 작업의 주체로 각 패키지 별로 다양한 설정을 할 수 있음
  - Root: 모든 패키지에 대한 로깅을 하기 위한 일반적인 로그 정책 설정 (한 개만 설정할 수 있음)
    - AppenderRef: 상단에 설정한 Appender를 참조

<br>



**로그를 파일로 저장하는 설정**

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<Configuration status="INFO">
    <Properties>
        <Property name="LOG_PATTERN">%d{HH:mm:ss.SSSZ} [%t] %-5level %logger{36} - %msg%n</Property>
    </Properties>
    <Appenders>
        <Console name="ConsoleLog" target="SYSTEM_OUT">
            <PatternLayout pattern="${LOG_PATTERN}" charset="UTF-8"/>
        </Console>
        <RollingFile name="FileLog"
                     fileName="./logs/spring.log"
                     filePattern="./logs/spring-%d{yyyy-MM-dd}-%i.log">
            <PatternLayout pattern="${LOG_PATTERN}" charset="UTF-8" />
            <Policies>
                <TimeBasedTriggeringPolicy interval="1" />
                <SizeBasedTriggeringPolicy size="10000KB" />
            </Policies>
            <DefaultRolloverStrategy max="20" fileIndex="min" />
        </RollingFile>
    </Appenders>
    <Loggers>
        <Root level="info">
            <AppenderRef ref="ConsoleLog" />
            <AppenderRef ref="FileLog" />
        </Root>
    </Loggers>
</Configuration>
```

<br>

- RollingFile: 조건에 따라 파일에 로그를 출력하도록 설정
  - name: 어펜더의 이름
  - fileName: 경로를 포함한 파일 이름
  - filePattern: 롤링 조건에 따른 경로를 포함한 파일 이름 패턴
  - Policies: 파일 롤링 정책
    - TimeBasedTriggeringPolicy: 1일 단위(interval=1)로 새로운 파일에 로그를 기록
    - SizeBasedTriggeringPolicy: 파일 사이즈를 기준으로 용량이 넘칠 경우 다음 파일을 생성하여 기록
    - DefaultRolloverStrategy: 파일 용량 초과 시 생성될 수 있는 파일의 최대 개수 설정
- 위와같이 설정하고나면, log파일이 springboot 아래의 logs 폴더가 생성되며 쌓이게 된다

