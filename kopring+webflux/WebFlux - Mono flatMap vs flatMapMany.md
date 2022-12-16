# WebFlux - flatMap vs flatMapMany

앞으로도 여러번 예제를 정리할 것 같다. 

# Mono -> List\<T\>, Mono -> Flux\<T\>

오늘은 Mono에서 `List<T>` 로 변환하는 경우와 `Flux<T>` 로 변환하는 경우에 대해 정리할 예정이다.<br>

뭔가 설명을 정리해야 하는데, 다른 공부도 밀려 있어서 잠깐 시간을 내서 정리한 예제를 그대로 코드에 정리해둬야 겠다. 나중에 다시 이 문서를 보면 미래의 나는 설명을 꼭 다시 정리할 것이라고 생각한ㄷ.... 내가 뭐... 진도준은 아니지만...<br>

참고로 Flux 의 모든 요소들을 테스트할 때 recordWith() -> consumeRecord() + hamcrest 를 사용한 매칭 검사를 사용하기도 하지만, Flux 의 모든 요소들이 원하는 순서로 있는지 테스트하고자 하는 경우는 이게 잘 안먹는다. 그래서 테스트 코드에서는 아래 예제처럼 StepVerifier.create(Flux.collectList()) 를 이용해 Mono\<List\<T\>\> 로 변환한 후에 kotlin 의 containsAll 메서드를 사용해 검증하는 것이 더 편리한 방법이다.<br>

책에서 알려주는 방법은 아니기에, 직접 좋은 방법들을 찾아가야 할듯!!<br>

```kotlin
@Test
fun test1(){
    val countryMono : Mono<String> = Mono.just("KOREA")

    // 1) flatMap : return Mono<List<City>>
    val cityListMono : Mono<List<City>> = countryMono.flatMap { getCityList(it).collectList() }

    val expectedCityList : List<City> = listOf(
        City("서울"), City("경기"), City("인천"),
        City("대전"), City("대구")
    )

    StepVerifier.create(cityListMono)
    .expectSubscription()
    .expectNextMatches {
        it.containsAll(expectedCityList)
    }
    .expectComplete()
    .verify()

    // 2) flatMapMany : return Flux<City>
    val fluxList : Flux<City> = countryMono.flatMapMany { getCityList(it) }

    StepVerifier.create(fluxList.collectList())
    .expectSubscription()
    .expectNextMatches { it.containsAll(
        listOf(
            City("서울"), City("경기"), City("인천"),
            City("대전"), City("대구")
        )
    ) }
    .expectComplete()
    .verify()

}

fun getCityList(city : String): Flux<City>{
    return Flux.just(
        City("서울"), City("경기"), City("인천"),
        City("대전"), City("대구")
    )
}

data class City(
    val name : String
)
```



