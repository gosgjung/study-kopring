# SOLID

쏠리드 하니깐 오늘 갑자기 [이게](https://www.mk.co.kr/star/musics/view/2018/05/312696/) 생각났었다.<br>

오늘 정리하는 개념은 [SOLID](https://ko.wikipedia.org/wiki/SOLID_(%EA%B0%9D%EC%B2%B4_%EC%A7%80%ED%96%A5_%EC%84%A4%EA%B3%84))이다. <br>

그냥 정리해봤다. 자바 기준으로만 공부하던 내용을 코틀린 기준으로 작성해보면서 복습도 할 겸 겸사겸사 정리를 시작했다.<br>

<br>

오늘은 각 5가기 원칙들에 대한 예제들을 코틀린 기반의 예제로 정리해보려 한다.<br>

<br>

# 참고자료

- [SOLID (객체지향 설계)](https://ko.wikipedia.org/wiki/SOLID_(%EA%B0%9D%EC%B2%B4_%EC%A7%80%ED%96%A5_%EC%84%A4%EA%B3%84))

<br>

# SOLID 의 5가지 원칙

- SRP (단일 책임 원칙) 
  - Single Responsibility Principle
  - 한 클래스는 하나의 책임만 가져야 한다.
- OCP (개방 폐쇄 원칙)
  - Open/Closed Principle
  - 소프트웨어 요소는 확장에는 열려있으나 변경에는 닫혀있어야 한다.
- LSP (리스코프 치환 원칙)
  - Liskov Substitution Principle
  - 프로그램의 객체는 프로그램의 정확성을 깨뜨리지 않으면서 하위 타입의 인스턴스로 바꿀 수 있어야 한다.
- ISP (인터페이스 분리 원칙)
  - Interface Segregation Principle
  - 특정 클라이언트를 위한 인터페이스 여러 개가 범용 인터페이스 하나보다 낫다.
- DIP (의존관계 역전 원칙)
  - Dependency Inversion Principle
  - 프로그래머는 추상화에 의존해야지, 구체화에 의존하면 안된다.
  - 예를 들면 스프링의 의존성 주입은 이원칙을 따르고 있다.

<br>

# SRP (Single Responsibility Principle)

- 한 클래스는 하나의 책임만 가져야 한다.
- 변경사항에 대한 수정 요구사항이 발생하면, 이 요구사항의 변경사항이 한 곳만 수정할 수 있게끔 해주면 좋다.

SRP 를 예로 들기 위해 주식데이터 처리를 예로 들어보려 한다. 미국 주식 데이터 서버로직을 작성하려고 하는 상황이라고 해보자.<br>

제일 처음 작성한 코드는 아래와 같다. 

```kotlin
@Service
class StockService (
    val stockRepository: StockRepository
){
    fun processSocketData1(currPrice: CurrPriceDto){
        // 1) 전달된 주식데이터를 미국 현지 시각으로 변환
        val translated = currPrice.tradeDateTimeInUTC()
            .withZoneSameInstant(ZoneId.of("America/New_York"))
            .toLocalDateTime()

        // 2) 주식 데이터가 나스닥 시장 거래 시각에 속하는지 검사
        if(translated.toLocalTime().isAfter(StockMarketTime.US.startTime)
            && translated.toLocalTime().isBefore(StockMarketTime.US.endTime)){
            stockRepository.save(currPrice)
        }
        else{
            return
        }
    }
}
```

<br>

위의 코드에서 주식 데이터가 장중 시간임을 체크하는 로직은 아래와 같다. 아래 로직은 SRP를 위배하고 있는 코드다.

```kotlin
// 2) 주식 데이터가 나스닥 시장 거래 시각에 속하는지 검사
if(translated.toLocalTime().isAfter(StockMarketTime.US.startTime)
&& translated.toLocalTime().isBefore(StockMarketTime.US.endTime)){
	stockRepository.save(currPrice)
}
else{
	return
}
```

<br>

소켓으로 들어오는 주식 데이터에 대해 +/- 5 초 정도의 오차를 두어서 그 시각 동안의 데이터도 장중 데이터로 취급해보자는 기획쪽의 변경사항 요청이 있었다고 해보자.<br>

이 경우 StockService 내의 processSocketData1 내의 코드를 변경해야만 하게 되었다.<br>

위의 경우는 가급적이면 요구사항에 대해서 StockService 내의 메서드를 직접 수정하는 대신에 별도의 클래스에에 기능을 분리해 해당 클래스만 수정하면 더 좋은 코드가 될 수 있다.<br>

<br>

아래와 같이 코드를 분리해보자.<br>

NasdaqMarketTimeChecker 라는 클래스에 isMarketTime () 이라는 메서드에 장중시간인지 체크하는 로직을 분리했다.<br>

```kotlin
package io.testprj.kopring_webflux.solid

import java.time.ZoneId
import java.time.ZonedDateTime

class NasdaqMarketTimeChecker {

    fun isMarketTime(zonedDateTime : ZonedDateTime) : Boolean {
        // 1) 전달된 주식데이터를 미국 현지 시각으로 변환
        val translatedDateTime = zonedDateTime
            .withZoneSameInstant(ZoneId.of("America/New_York"))
            .toLocalDateTime()

        // 2) 주식 데이터가 나스닥 시장 거래 시각에 속하는지 검사
        return (translatedDateTime.toLocalTime().minusSeconds(-5).isAfter(StockMarketTime.US.startTime)
                && translatedDateTime.toLocalTime().plusSeconds(5).isBefore(StockMarketTime.US.endTime))
    }
}
```

<br>

그리고 이제 StocService 의 코드는 아래와 같이 수정되었다

```kotlin
package io.testprj.kopring_webflux.solid

import org.springframework.stereotype.Service

@Service
class StockService (
    val stockRepository: StockRepository,
    val marketTimeChecker: NasdaqMarketTimeChecker
){

    fun processSocketData1(currPrice: CurrPriceDto){
        // 장중 시간이 아닐 경우 process 를 진행하지 않고 리턴
        if(!marketTimeChecker.isMarketTime(currPrice.tradeDateTimeInUTC()))
            return
        
        // 장중 시간일 경우 데이터 저장
        stockRepository.save(currPrice)
    }

}
```

<br>

구체적으로는 아래의 부분이 MarketTimeChecker 클래스의 isMarketTime(ZonedDateTime) 메서드를 사용하게끔 변경되었다.

```kotlin
fun processSocketData1(currPrice: CurrPriceDto){
    // 장중 시간이 아닐 경우 process 를 진행하지 않고 리턴
    if(!marketTimeChecker.isMarketTime(currPrice.tradeDateTimeInUTC()))
    return

    // 장중 시간일 경우 데이터 저장
    stockRepository.save(currPrice)
}
```

<br>

이렇게 하해서 장중 시간 체크 기능에 대한 여러가지 변경 요구사항에 대해서는 `NasdaqMarketTimeChecker` 클래스가 담당하게 되었다. 즉, 시간 체크 기능에 대해서는 `NasdaqMarketTimeChecker` 클래스가 전담하게 되었다. (시간 체크 기능에 대해서는 `NasdaqMarketTimeChecker` 클래스가 단일책임으로 전담하게 되었다.)<br>

<br>

# OCP (Open/Closed Principle)

- 소프트웨어 요소는 확장에는 열려 있으나 변경에는 닫혀있어야 한다.

확장에는 열려있다.

- 기존의 코드를 수정하지 않으면서 새로운 기능을 추가/변경 가능해야 한다.

변경에는 닫혀있다.

- 새로운 기능 추가/변경시 기존의 코드가 변경되지 않아야 한다.

<br>

이번에는 뜬금없지만, StockService가 미국주식이 아닌, 한국주식을 처리하기로 했다고 해보자. 그런데 현재 코드의 구조로는 StockService를 수정해야만 한국주식 장중시간 체크 로직을 추가할 수 있다.<br>

변경되어야하는 문제의 부분을 `//***` 으로 표시해뒀다.

```kotlin
package io.testprj.kopring_webflux.solid

import org.springframework.stereotype.Service

@Service
class StockService (
    val stockRepository: StockRepository,
    //***  
    // 아래 부분이 문제다. 부분을 KoreaMarketTimeChecker 를 사용하게끔 바꿔줘야 하게 되었다.
    val marketTimeChecker: NasdaqMarketTimeChecker,
){

    fun processSocketData1(currPrice: CurrPriceDto){
        // 장중 시간이 아닐 경우 process 를 진행하지 않고 리턴
        if(!marketTimeChecker.isMarketTime(currPrice.tradeDateTimeInUTC()))
            return

        // 장중 시간일 경우 데이터 저장
        stockRepository.save(currPrice)
    }

}
```

<br>

이제 이런 상황에서, isMarketTime() 이라는 메서드를 갖는 MarketTimeChecker 라는 인터페이스를 정의하고, 이 인터페이스를 NasdaqMarketTimeChecker, KrStockMarketTimeChecker 클래스가 implement 하게끔 하기로 했다고 해보자.<br>

<br>

**MarketTimeChecker.kt**<br>

아래는 MarketTimeChecker 라는 이름의 인터페이스이고, isMarketTime() 이라는 메서드를 가지게끔 선언했다.

```kotlin
package io.testprj.kopring_webflux.solid

import java.time.ZonedDateTime

interface MarketTimeChecker {
    
    fun isMarketTime(zonedDateTime : ZonedDateTime) : Boolean
    
}
```

<br>

**NasdaqMarketTimeChecker.kt**<br>

NasdaqMarketTimeChecker 는 MarketTimeChecker 를 implements 하도록 해주었다.<br>

> 코틀린에서는 `@Override` 와 같이 표현하지 않고 `override` 라는 키워드를 사용한다.

<br>

```kotlin
package io.testprj.kopring_webflux.solid

import java.time.ZoneId
import java.time.ZonedDateTime

class NasdaqMarketTimeChecker : MarketTimeChecker{

    override fun isMarketTime(zonedDateTime : ZonedDateTime) : Boolean {
        // 1) 전달된 주식데이터를 미국 현지 시각으로 변환
        val translatedDateTime = zonedDateTime
            .withZoneSameInstant(ZoneId.of("America/New_York"))
            .toLocalDateTime()

        // 2) 주식 데이터가 나스닥 시장 거래 시각에 속하는지 검사
        return (translatedDateTime.toLocalTime().minusSeconds(-5).isAfter(StockMarketTime.US.startTime)
                && translatedDateTime.toLocalTime().plusSeconds(5).isBefore(StockMarketTime.US.endTime))
    }
}
```

<br>

그리고 새로 추가할 기능인 KrStockMarketTimeChecker 클래스 역시도 정의해주자.

```kotlin
package io.testprj.kopring_webflux.solid

import java.time.ZoneId
import java.time.ZonedDateTime

class KrStockMarketTimeChecker : MarketTimeChecker{
    // 1) 전달된 주식데이터를 한국 현지 시각으로 변환
    override fun isMarketTime(zonedDateTime: ZonedDateTime): Boolean {
        val translatedDateTime = zonedDateTime
            .withZoneSameInstant(ZoneId.of("Asia/Seoul"))
            .toLocalDateTime()

        return (translatedDateTime.toLocalTime().isAfter(StockMarketTime.KR.startTime)
                && translatedDateTime.toLocalTime().isBefore(StockMarketTime.KR.endTime))
    }
}
```

<br>

**StockService.kt**<br>

StockService 에서는 이전에는 NasdaqMarketTimeChecker 타입을 의존성으로 가지고 있는 것을 이것의 타입을 

interface 타입인 MarketTimeChecker 로 수정해주자.<br>

```kotlin
package io.testprj.kopring_webflux.solid

import org.springframework.stereotype.Service

@Service
class StockService (
    val stockRepository: StockRepository,
    //***
    // 이 부분을 수정해줬다.
    val marketTimeChecker: MarketTimeChecker,
){

    fun processSocketData1(currPrice: CurrPriceDto){
        // 장중 시간이 아닐 경우 process 를 진행하지 않고 리턴
        if(!marketTimeChecker.isMarketTime(currPrice.tradeDateTimeInUTC()))
            return

        // 장중 시간일 경우 데이터 저장
        stockRepository.save(currPrice)
    }

}
```

<br>

위의 코드는 아래와 같이 사용할 수 있게 된다.

```kotlin
@Test
fun `개방 폐쇄 원칙`(){
    val stockService = StockService(StockRepository(), KrStockMarketTimeChecker())

    val currData = CurrPriceDto(
        currPrice = BigDecimal.valueOf(100500),
        ticker = "삼성전자",
        tradeDate = LocalDate.of(2022, 9, 30),
        tradeTime = LocalTime.of(0,0,1) // UTC 기준 한국 시장 개장시각
    )

    stockService.processSocketData1(currData)
}
```

<br>

# LSP (Liskov Substitution Principle)





# ISP (Interface Segregation Principle)





# DIP (Dependency Inversion Principle)











