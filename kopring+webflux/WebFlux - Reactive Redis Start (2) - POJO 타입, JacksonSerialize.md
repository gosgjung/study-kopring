# WebFlux - Reactive Redis Start (2) - POJO 타입, JacksonSerialize

문서의 제목을 뭘로 할지 굉장히 고민하다가, 거지같은 제목으로 지어버렸다...ㅋㅋ<br>

코틀린의 경우 자바에 비해 기본 생성자라든가 이런 것들 때문에 별도로 해줘야 하는 작업들이 조금 있다.<br>

이번 문서에서는 아래의 내용을 정리한다.<br>

<br>

- `POJO` 타입에서의 `BigDecimal` 타입 필드에 대한 Jackson Serialize
  - 별도의 문서에 정리해둘 예정
- 코틀린에서 `Jackson Serialize` 커스터마이징
  - 코틀린에서는 자바와는 다르게 어느 정도의 커스터마이징이 필요하다.
  - 기본 생성자를 강제로 사용할 수 있게끔 지정하거나 또는 KotlinModule 클래스 내부에 있는 Builder 클래스를 통해 KotlinModule 인스턴스를 생성해서 사용하는 방식이다.
    - 별도의 문서([Kotlin 에서 Jackson Serialize, Deserialize 관련 문제](https://github.com/gosgjung/study-kopring/blob/main/Kotlin%20%EC%97%90%EC%84%9C%20Jackson%20Serialize%2C%20Deserialize%20%EA%B4%80%EB%A0%A8%20%EB%AC%B8%EC%A0%9C.md))에 정리해두었다.
- RedisTemplate 생성시 value 에대한 Serializer의 ObjectMapper 를 별도로 지정하기
  - `KotlinModule` 이 적용된 ObjectMapper를 RedisTemplate 의 ValueSerializer의 objectMapper로 지정해주는 작업이다.

<br>

# 참고자료

- Kotlin 에서 ObjectMapper 사용
  - https://kapentaz.github.io/kotlin/json/Kotlin-and-Jackson-(ObjectMapper)
- Kotlin 에서 BigDecimal 필드에 JsonSerialize
  - https://stackoverflow.com/questions/52149589/return-bigdecimal-fields-as-json-string-values-in-java
  - https://stackoverflow.com/questions/11319445/java-to-jackson-json-serialization-money-fields
  - https://jsonobject.tistory.com/466

<br>



# Redis 접속 설정

Reids Connection 설정 코드는 아래와 같다.<br>

<br>

`ReactiveRedisConnectionFactoryConfig.kt`

```kotlin
package io.study.gosgjung.reactive_redis.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory

@Configuration
class ReactiveRedisConnectionFactoryConfig (
    @Value("\${just-test.redis.host}")
    val host: String,

    @Value("\${just-test.redis.port}")
    val port: Int
){
    @Bean(name = ["reactiveRedisConnectionFactory"])
    fun reactiveRedisConnectionFactory() : ReactiveRedisConnectionFactory {
        return LettuceConnectionFactory(host, port)
    }
}
```

<br>



# RedisTemplate 선언 및 정의

**ObjectMapper 빈 생성코드**<br>

그냥 RedisTemplate 생성하는 함수 안에서 인스턴스 생성해서 결합할 수도 있겠지만, 다른 RedisTemplate 생성할때도 재사용하기 위해서 아래와 같이 선언해줬다.<br>

```kotlin
@Bean(name = ["redisObjectMapper"])
fun redisObjectMapper() : ObjectMapper{
    val kotlinModule = KotlinModule.Builder()
    .withReflectionCacheSize(512)
    .configure(KotlinFeature.NullToEmptyCollection, false)
    .configure(KotlinFeature.NullToEmptyMap, false)
    .configure(KotlinFeature.NullIsSameAsDefault, enabled = true)
    .configure(KotlinFeature.StrictNullChecks, false)
    .build()

    return ObjectMapper().registerModule(kotlinModule)
}
```

<br>

Kotlin 에서 ObjectMapper를 사용하기 위해서는 크게 아래의 2가지 방법을 거치게 된다.

- `@JsonCreator` 라는 어노테이션과 함께 생성자를 따로 만들어서 사용하는 방법 
- ObjectMapper 객체 생성시 KotlinModule 을 사용하는 ObjectMapper 객체를 생성하는 방법
  - 위 예제에서 사용한 방법이다.

<br>

이 중에서 두번째 방법이 KotlinModule 클래스 내부의 Builder 클래스를 이용해 커스터마이징도 편하기에 두번째방법을 개인적으로 선호하는 편이다. 그래서 예제도 위 예제처럼 별도의 `KotlinModule.Builder` 클래스를 이용해 옵션들을 설정 후 ObjectMapper 를 생성하는 방식을 선택했다.<br>

<br>

**RedisTemplate 정의**<br>

키/밸류를 Serialize, Deserialize 할 수 있도록 Serializer를 지정하고, 키/밸류의 타입을 지정하는 설정코드를 작성하자.<br>

```kotlin
@Bean(name = ["stockMap"])
fun stockMap(
    reactiveRedisConnectionFactory: ReactiveRedisConnectionFactory,
    @Qualifier("redisObjectMapper") objectMapper: ObjectMapper // 1)
) : ReactiveRedisTemplate<String, StockDto>{
    val keySerializer: StringRedisSerializer = StringRedisSerializer()
    val valSerializer: Jackson2JsonRedisSerializer<StockDto> = Jackson2JsonRedisSerializer<StockDto>(StockDto::class.java)
    valSerializer.setObjectMapper(objectMapper) // 2)

    val builder: RedisSerializationContextBuilder<String, StockDto> =
    RedisSerializationContext.newSerializationContext<String, StockDto>(keySerializer)

    val context: RedisSerializationContext<String, StockDto> = builder
    .key(keySerializer)
    .value(valSerializer)
    .hashKey(keySerializer)
    .hashValue(valSerializer)
    .build()

    return ReactiveRedisTemplate(reactiveRedisConnectionFactory, context)
}
```

<br>

- `1)` : 위에서 Bean 으로 선언해둔 ObjectMapper 인스턴스를 주입받았다.
  - 외부 API 값의 Null값 발생시 디폴트 값 처리 등이 적용된 KotlinModule을 ObjectMapper 에 지정한 인스턴스다.
- `2)` : `1)` 에서 커스텀하게 정의한 ObjectMapper 를 Jackson2JsonRedisSerializer 인스턴스 내에 objectMapper로 지정해줬다.

<br>



RebbitTemplate 선턴 코드 전체 내용

```kotlin
package io.study.gosgjung.reactive_redis.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinFeature
import com.fasterxml.jackson.module.kotlin.KotlinModule
import io.study.gosgjung.reactive_redis.study.StockDto
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory
import org.springframework.data.redis.core.ReactiveRedisTemplate
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer
import org.springframework.data.redis.serializer.RedisSerializationContext
import org.springframework.data.redis.serializer.RedisSerializationContext.RedisSerializationContextBuilder
import org.springframework.data.redis.serializer.StringRedisSerializer

@Configuration
class RedisConfig (
){
    // ...
    
    @Bean(name = ["redisObjectMapper"])
    fun redisObjectMapper() : ObjectMapper{
        val kotlinModule = KotlinModule.Builder()
            .withReflectionCacheSize(512)
            .configure(KotlinFeature.NullToEmptyCollection, false)
            .configure(KotlinFeature.NullToEmptyMap, false)
            .configure(KotlinFeature.NullIsSameAsDefault, enabled = true)
            .configure(KotlinFeature.StrictNullChecks, false)
            .build()

        return ObjectMapper().registerModule(kotlinModule)
    }

    @Bean(name = ["stockMap"])
    fun stockMap(
        reactiveRedisConnectionFactory: ReactiveRedisConnectionFactory,
        @Qualifier("redisObjectMapper") objectMapper: ObjectMapper
    ) : ReactiveRedisTemplate<String, StockDto>{
        val keySerializer: StringRedisSerializer = StringRedisSerializer()
        val valSerializer: Jackson2JsonRedisSerializer<StockDto> = Jackson2JsonRedisSerializer<StockDto>(StockDto::class.java)
        valSerializer.setObjectMapper(objectMapper)

        val builder: RedisSerializationContextBuilder<String, StockDto> =
            RedisSerializationContext.newSerializationContext<String, StockDto>(keySerializer)

        val context: RedisSerializationContext<String, StockDto> = builder
            .key(keySerializer)
            .value(valSerializer)
            .hashKey(keySerializer)
            .hashValue(valSerializer)
            .build()

        return ReactiveRedisTemplate(reactiveRedisConnectionFactory, context)
    }
}
```

<br>



# Dto 내의 BigDecimal 타입 필드 JsonSerialize 

`StockDto.kt` 

```kotlin
package io.study.gosgjung.reactive_redis.study

import com.fasterxml.jackson.databind.annotation.JsonSerialize
import java.math.BigDecimal

data class StockDto(

    val ticker: String = "!!!NOT-ASSIGNED",

    @JsonSerialize(using = BigDecimalRounding6Serializer::class) // 1) 
    val lastPrice: BigDecimal = BigDecimal.valueOf(0.0)

)
```

<br>

- `1)` 에서 지정한 `BigDecimalRounding6Serializer` 클래스는 직접 별도로 정의해둔 클래스다.
  - 아래에 코드로 정리했다.

<br>

`BigDecimalRounding6Serializer.kt`

```kotlin
package io.study.gosgjung.reactive_redis.study

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.JsonSerializer
import com.fasterxml.jackson.databind.SerializerProvider
import java.math.BigDecimal
import java.math.RoundingMode

class BigDecimalRounding6Serializer : JsonSerializer<BigDecimal>(){

    companion object {
        const val SCALE_SIX = 6
        val ROUNDING_MODE = RoundingMode.HALF_EVEN
    }

    override fun serialize(value: BigDecimal?, gen: JsonGenerator?, serializers: SerializerProvider?) {
        // 1)
        gen?.writeString(value?.setScale(SCALE_SIX, ROUNDING_MODE).toString())
    }

}
```

<br>

- `1)` 소숫점 6번째 아래까지 반올림을 하도록 처리해줬다.
  - 이렇게 클래스로 생성해두면 사용하려는 반올림 방식에 따라 Serializer를 생성해서 커스텀하게 적용해줄 수 있다

<Br>



# 테스트

이제 대망의 테스트 코드다.

```kotlin
package io.study.gosgjung.reactive_redis.study

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.redis.core.ReactiveRedisTemplate
import reactor.test.StepVerifier
import java.math.BigDecimal

@SpringBootTest
class ReactiveRedisHashTest {

    @Autowired
    @Qualifier("playerAgeMap")
    lateinit var playerAgeMap : ReactiveRedisTemplate<String, String>

    @Autowired
    @Qualifier("stockMap")
    lateinit var stockMap : ReactiveRedisTemplate<String, StockDto>

    // ... 

    @Test
    fun `JacksonSerializer를 테스트해보자`(){
        val hash = stockMap.opsForHash<String, StockDto>()
        val namespace = "USStockHash"
        hash.putIfAbsent(namespace, "MSFT", StockDto(ticker = "MSFT", lastPrice = BigDecimal.valueOf(240.05))).subscribe()
        hash.putIfAbsent(namespace, "AAPL", StockDto(ticker = "AAPL", lastPrice = BigDecimal.valueOf(132.37))).subscribe()
        hash.putIfAbsent(namespace, "AMZN", StockDto(ticker = "AMZN", lastPrice = BigDecimal.valueOf(84.92))).subscribe()

        val koreaStockMap = buildMap<String, StockDto>{
            put("MSFT", StockDto(ticker = "MSFT", lastPrice = BigDecimal.valueOf(240.05)))
            put("AAPL", StockDto(ticker = "AAPL", lastPrice = BigDecimal.valueOf(132.37)))
            put("AMZN", StockDto(ticker = "AMZN", lastPrice = BigDecimal.valueOf(84.92)))
        }

        koreaStockMap.map{
            val ticker = it.key

            StepVerifier.create(hash.get(namespace, ticker))
                .expectSubscription()
                .expectNextMatches { it.ticker == ticker }
                .expectComplete()
                .verify()
        }
    }

}
```















