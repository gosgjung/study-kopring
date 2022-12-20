# Kotlin 에서 Jackson Serialize, Deserialize 관련 문제 

# 참고자료

https://kapentaz.github.io/kotlin/json/Kotlin-and-Jackson-(ObjectMapper)/

<br>

# StockDto

직렬화/역직렬화 하려는 kotlin 클래스는 아래와 같다.

```kotlin
package io.study.gosgjung.reactive_redis.study

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import java.io.Serializable
import java.math.BigDecimal

data class StockDto(

    val ticker: String,

    @JsonProperty("lastPrice")
    @JsonSerialize(using = BigDecimalRounding6Serializer::class)
    val lastPrice: BigDecimal

)
```

<Br>



# 에러 메시지

에러 메시지는 아래와 같다.

```
Caused by: com.fasterxml.jackson.databind.exc.InvalidDefinitionException: Invalid type definition for type `io.study.gosgjung.reactive_redis.study.StockDto`: Argument #0 of constructor [constructor for `io.study.gosgjung.reactive_redis.study.StockDto` (2 args), annotations: [null] has no property name annotation; must have name when multiple-parameter constructor annotated as Creator
```

<br>

creator 로 constructor 를 추가해라 등의 이야기가 있는데, 위에 맞게끔 하려면 아래의 `해결방법 1)` 대로 하면 된다.<br>

이 외의 해결책은 3가지가 있다. 해결방법2), 해결방법3), 해결방법 4) 에 정리해두었다.<br>

<br>



# 해결방법 1)

개인적으로는 좋은 방법은 아니라고 생각한다. 

```kotlin
package io.study.gosgjung.reactive_redis.study

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import java.io.Serializable
import java.math.BigDecimal

data class StockDto @JsonCreator constructor(

    val ticker: String,

    @JsonProperty("lastPrice")
    @JsonSerialize(using = BigDecimalRounding6Serializer::class)
    val lastPrice: BigDecimal

)
```

<br>

# 해결방법 2) KotlinModule 을 사용하기

현재는 deprecated 된 방법이다.<br>

`KotlinModule` 은 `jackson-module-kotlin` 에서 제공하는 클래스다.<br>

<Br>

```kotlin
@Test
fun test1(){
    // KotlinModule 은 Deprecated 되어있다.
    val mapper1 = ObjectMapper().registerModule(KotlinModule())
    val str1 = mapper1.writeValueAsString(StockDto(ticker = "MSFT", lastPrice = BigDecimal.valueOf(240.00)))
    val data1 = mapper1.readValue<StockDto>(str1, StockDto::class.java)
    logger.info("\ndata = $data1 \n")
}
```

<br>



# 해결방법 3) jacksonObjectMapper() 를 사용하기

`jackson-module-kotlin` 에서 제공하는 확장함수인 jacksonObjectMapper() 를 사용하는 방식이다.

```kotlin
@Test
fun test2(){
    val mapper2 = jacksonObjectMapper()
    val str2 = mapper2.writeValueAsString(StockDto(ticker = "MSFT", lastPrice = BigDecimal.valueOf(240.00)))
    val data2 = mapper2.readValue<StockDto>(str2, StockDto::class.java)
    logger.info("\ndata = $data2 \n")
}
```

<br>



# 해결방법 4) ObjectMapper 객체 생성시 코틀린 Jackson 모듈 사용하는 객체를 생성

빈으로 등록해서 사용할 때 아래처럼 해도 되겠다 싶다.

```kotlin
@Test
fun test3(){
    val mapper3 = ObjectMapper().registerKotlinModule()
    val str3 = mapper3.writeValueAsString(StockDto(ticker = "MSFT", lastPrice = BigDecimal.valueOf(240.00)))
    val data3 = mapper3.readValue<StockDto>(str3, StockDto::class.java)
    logger.info("\ndata = $data3 \n")
}
```

<br>



# null 데이터 전달시 기본값 처리

코틀린은 객체 생성시 값을 지정하지 않을 경우에 대한 기본 값을 지정할 수 있다. 하지만, 외부에서 오는 데이터가 null 이 전달되는 경우에 대해서도 처리해야 한다. 외부에서 전달되는 데이터는 String 으로 직렬화되어서 우리 기계로 들어오는데, 이 데이터를 Json 으로 Deserialize 할 때 어떻게 처리할 지를 Jackson에게 지정해주는 것이다.<br>

예를 들면 위에서 예로 든 StockDto를 아래와 같이 기본값들을 지정해줬다고 해보자.

```kotlin
package io.study.gosgjung.reactive_redis.study

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import java.math.BigDecimal

data class StockDto(

    val ticker: String,

    @JsonProperty("lastPrice")
    @JsonSerialize(using = BigDecimalRounding6Serializer::class)
    val lastPrice: BigDecimal

)
```

<br>

위의 코드 까지는 외부 통신을 하지 않는한, 프로그램 내부에서의 객체 생성시에는 아무 문제가 없다.

```kotlin
@Test
fun test4(){
    val mapper4 = ObjectMapper().registerKotlinModule()
    val str4 = mapper4.writeValueAsString(StockDto())
    val data4 = mapper4.readValue<StockDto>(str4, StockDto::class.java)
    logger.info("\ndata = $data4")
}
```

<br>



만약 외부 API와 통신할 때에는, null 로 데이터가 찍혀져 오는 경우도 당연히 있다. 이런 케이스에 대한 처리 역시 필요하다. 이 경우 아래와 같이 `KotlinModule`내의 `nullisSameAsDefault` 속성을 true 로 변경해줘서 처리해보자.

```kotlin
@Test
fun test5(){
    val mapper5 = ObjectMapper().registerModule(KotlinModule(nullIsSameAsDefault = true))
    val str5 = mapper5.writeValueAsString(StockDto())
    val data5 = mapper5.readValue<StockDto>(str5, StockDto::class.java)
    logger.info("\ndata = $data5")
}
```



이렇게 하면, 아래와 같이 null 일 경우에 대한 데이터가 모두 바인딩되어 출력되는 것을 볼 수 있다.

```plain
data = StockDto(ticker=!!!NOT-ASSIGNED, lastPrice=0.000000)
```

<br>

하지만, KotlinModule 생성자를 바로 호출하는 것은 `Deprecated` 되어 있다. 따라서 아래와 같이 Builder 를 사용해서 필요한 속성들을 지정해주자.<br>

```kotlin
@Test
fun test6(){
    val kotlinModule = KotlinModule.Builder()
    .withReflectionCacheSize(512)
    .configure(KotlinFeature.NullToEmptyCollection, false)
    .configure(KotlinFeature.NullToEmptyMap, false)
    .configure(KotlinFeature.NullIsSameAsDefault, enabled = true)
    .configure(KotlinFeature.StrictNullChecks, false)
    .build()

    val mapper6 = ObjectMapper().registerModule(kotlinModule)
    val str6 = mapper6.writeValueAsString(StockDto())
    val data6 = mapper6.readValue<StockDto>(str6, StockDto::class.java)
    logger.info("\ndata = $data6")
}
```

<br>

