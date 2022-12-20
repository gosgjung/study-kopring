# Kotlin - BigDecimal 필드 Jackson Serialize

BigDecimal 필드 JsonSerialize 는 kotlin 뿐만 아니라 자바에도 적용되는 내용이다.<br>

다만, 코틀린 코드이기에, companion object를 사용한 코드가 있다. 이 코드는 자바에 맞게끔 바꿔서 쓰면 될듯<br>

굳이 kopring-webflux 디렉터리보다는 조금 더 general 한 주제를 가진 별도의 코틀린 공부 폴더에 정리해둬야 겠다는 생각이 들었지만, 내 성격상 이름 짓는 걸 너무 괴로워한다. 그래서 나중에 생각나면 별도의 폴더에 모아둬야 할 것 같다.<br>

<bR>



# 참고자료

Kotlin 에서 BigDecimal 필드에 JsonSerialize

- https://stackoverflow.com/questions/52149589/return-bigdecimal-fields-as-json-string-values-in-java
- https://stackoverflow.com/questions/11319445/java-to-jackson-json-serialization-money-fields
- https://jsonobject.tistory.com/466

<br>



# Pojo

예를 들어 아래와 같은 `StockDto` 라는 클래스가 있다고 해보자.<br>

`StockDto.kt`

```kotlin
package io.study.gosgjung.reactive_redis.study

import com.fasterxml.jackson.databind.annotation.JsonSerialize
import java.math.BigDecimal

data class StockDto(

    val ticker: String = "!!!NOT-ASSIGNED",

    // 1)
    @JsonSerialize(using = BigDecimalRounding6Serializer::class)
    val lastPrice: BigDecimal = BigDecimal.valueOf(0.0)

)
```

<br>

- `1)` : @JsonSerialize 애노테이션을 사용했다. 그리고 별도로 정의해둔 `BigDecimalRounding6Serializer` 라는 클래스를 사용해 `BigDecimal` 타입의 필드에 대해 Serialize/Deserialize 하도록 애노테이션을 지정했다.

<br>

# JsonSerializer

`JsonSerializer` 클래스는 jackson 모듈에서 제공하는 Serialize, Deserialize 를 위한 클래스다.<br>

소숫점 6번째 자리에서 반올림하게끔 지정해줬다. 자세한 코드 설명은 생략!! 하기로 결정!!<br>

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
        gen?.writeString(value?.setScale(SCALE_SIX, ROUNDING_MODE).toString())
    }

}
```

<br>

# 테스트 코드

코틀린에서 ObjectMapper를 사용하는 것은 처음 공부할 때는 어느 정도는 고생해서 자료를 조사해야 한다.

코틀린에서 작성한 Pojo 클래스에 ObjectMapper를 사용하기 위해 KotlinModule 객체를 생성하는 것에 대한 내용은 아래의 자료에 정리해두었다.

- [Kotlin 에서 Jackson Serialize, Deserialize 관련 문제](https://github.com/gosgjung/study-kopring/blob/main/Kotlin%20%EC%97%90%EC%84%9C%20Jackson%20Serialize%2C%20Deserialize%20%EA%B4%80%EB%A0%A8%20%EB%AC%B8%EC%A0%9C.md)

<br>

이번 예제에서는 KotlinModule 클래스 내에 `Builder` 패턴으로 정의되어 있는 `KotlinModule.Builder` 크래스를 활용해 필요한 필드 처리 옵션을 지정하는 방식을 사용했다. (개인적으로는 `KotlinModule.Builder` 를 사용해 설정하는 것이 귀찮긴 하더라도 제일 좋은 방식인 것 같다.)<br>

<br>

```kotlin
@Test
fun test6(){
    // 1) 
    val kotlinModule = KotlinModule.Builder()
        .withReflectionCacheSize(512)
        .configure(KotlinFeature.NullToEmptyCollection, false)
        .configure(KotlinFeature.NullToEmptyMap, false)
        .configure(KotlinFeature.NullIsSameAsDefault, enabled = true)
        .configure(KotlinFeature.StrictNullChecks, false)
        .build()

    // 2)
    val mapper6 = ObjectMapper().registerModule(kotlinModule)
    
    // 3) 
    val str6 = mapper6.writeValueAsString(StockDto())
    val data6 = mapper6.readValue<StockDto>(str6, StockDto::class.java)
    logger.info("\ndata = $data6")
}
```



- `1)` : KotlinModule 객체를 KotlinModule.Builder 클래스를 이용해 생성
- `2)` : ObjectMapper 내에 module로 kotlinModule 객체를 등록
- `3)` : 테스트를 위한 Json 문자열 생성
- `4)` : Json 문자열을 `StockDto` 타입으로 변환

<br>

출력결과

```plain
data = StockDto(ticker=!!!NOT-ASSIGNED, lastPrice=0.000000)
```

<br>



