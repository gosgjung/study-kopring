# 클래스, 프로퍼티, backing field

이거 정리할 때 왜이렇게 지루해서... 자꾸 잠깐 쉬고를 반복했는지... 그래도 다 정리했다. 흐흐<br>

<br>

# 1. 클래스

## 클래스의 선언

java 처럼 kotlin 역시 클래스를 선언하는 것은 `class` 키워드를 사용해서 선언할 수 있다.

```kotlin
class TickerMeta{
    // ...
}
```

<br>

코틀린의 클래스는 본문, 몸체를 생략하는 것도 가능하다.(신기방기)

```kotlin
class TickerMeta
```

<br>

## 생성자 선언

코틀린의 생성자는 기본 생성자 외에도 하나 이상의 보조 생성자를 가질 수 있다.<br>

생성자 선언시에는 `constructor` 라는 키워드를 사용한다.<br>

기본 생성자는 `constructor` 키워드를 생략하고 사용가능하다.<br>

toString은 기본으로 생성되지 않는다.<br>

<br>

**기본 생성자 생략**<br>

만약 클래스 내에 생성자를 하나만 선언할 경우에는 `constructor` 를 생략하고 아래와 같이 선언 가능하고, 아래와 같이 선언한 생성자가 기본생성자가 될 수 있다.<br>

```kotlin
class TickerMeta1(val ticker: String, val zoneId: ZoneId)

fun main(){
    val t1 = TickerMeta1(ticker = "AAPL", zoneId = ZoneId.of("America/New_York"))
    println("t1.ticker = ${t1.ticker} , t1.zoneId = ${t1.zoneId}")
    println()
}
```

<br>

출력결과

```plain
t1.ticker = AAPL , t1.zoneId = America/New_York
```

<br>

**생성자 여러개 사용해보기**<br>

기본 생성자 외에 다른 생성자를 선언하는 방식은 아래와 같다.<br>

```kotlin
class TickerMeta2(val ticker: String){
    var zoneId: ZoneId = ZoneId.of("America/New_York")
    var earning: BigDecimal = BigDecimal.valueOf(0)
    
    constructor(ticker: String, zoneId: ZoneId, earning: BigDecimal) : this(ticker){
        this.zoneId = zoneId
        this.earning = earning
    }
}
```

<br>

출력결과

```plain
t2.ticker = AAPL , t2.zoneId = America/New_York , t2.earning = 30000
```

<br>

# 2. 프로퍼티

## var, val

코틀린의 프로퍼티는 `var`, `val` 를 사용하는 것이 모두 허용된다.<br>

```kotlin
class TickerMeta1(
    val ticker: String,
    var price: BigDecimal
)

fun main(){
    val t1 = TickerMeta1(ticker = "AAPL", price = BigDecimal.valueOf(20000))
    println("t1.ticker = ${t1.ticker} , t1.price = ${t1.price}")
}
```

<br>

출력결과

```plain
t1.ticker = AAPL , t1.price = 20000
```

<br>

## 참조연산 (getter 없이 바로 접근 가능하다.)

프로퍼티를 수정하거나 사용할 때 참조연산자를 사용해서 접근해 수정할 수 있다.<br>

```kotlin
class TickerMeta1(
    val ticker: String,
    var price: BigDecimal
)

fun main(){
    val t2 = TickerMeta1(ticker = "AAPL", price = BigDecimal.valueOf(20000))
    t2.price = BigDecimal.valueOf(30000)
    println("t2.ticker = ${t2.ticker} , t2.price = ${t2.price}")
    println()
}
```

<br>

출력결과

```plain
t2.ticker = AAPL , t2.price = 30000
```

<br>

# 3. getter/setter

**`var`, `val` 에 따라 다른 `getter`, `setter` 사용방식**<br>

`var` 로 선언된 프로퍼티는 `getter`, `setter` 가 내부적으로 자동으로 생성된다.<br>

- var 로 선언된 프로퍼티는 `getter`, `setter` 를 모두 가지고 있는다.

반면, `val` 로 선언된 프로퍼티는 `getter` 가 내부적으로 자동으로 생성된다.<br>

- val 로 선언된 프로퍼티는 `getter` 만 가지고 있는다.

<br>

**커스텀 getter/setter**<br>

코틀린은 커스텀 `getter`, `setter` 를 만들어서 사용가능하다.<br>

코틀린은 `getter`, `setter` 에서 `field` 라는 식별자를 통해 필드의 참조에 접근한다. <br>

이렇게 필드의 참조를 `field` 라는 식별자로 접근하는 방식을 `Backing Field` 에 접근한다. 라고 이야기한다.<br>

<br>

## 기본 getter/setter 가 사용되는 경우

기본 setter 를 사용하는 경우는 아래와 같다.

```kotlin
class TickerMeta1(
    val ticker: String,
    var price: BigDecimal
)

fun main(){
    val t2 = TickerMeta1(ticker = "AAPL", price = BigDecimal.valueOf(20000))
    t2.price = BigDecimal.valueOf(30000)
}
```

<br>

위의 코드에서처럼 `t2.price = BigDecimal.valueOf(30000)` 과 같은 코드로 값을 할 당하는 코드를 실행할 때는 기본 setter 를 사용하게 된다.<br>

<br>

기본 getter 를 사용하는 경우는 아래와 같다. 참조 연산자로 접근하고 있다.

```kotlin
class TickerMeta1(
    val ticker: String,
    var price: BigDecimal
)

fun main(){
    val t2 = TickerMeta1(ticker = "AAPL", price = BigDecimal.valueOf(20000))
    t2.price = BigDecimal.valueOf(30000)
    println("t2.ticker = ${t2.ticker} , t2.price = ${t2.price}")
    println()
}
```

<br>

## 커스텀 getter, setter

코틀린은 커스텀 `getter`, `setter` 를 만들어서 사용가능하다.<br>

코틀린은 `getter`, `setter` 에서 `field` 라는 식별자를 통해 필드의 참조에 접근한다. <br>

이렇게 필드의 참조를 `field` 라는 식별자로 접근하는 방식을 `Backing Field` 에 접근한다. 라고 이야기한다.<br>

커스텀 `setter` 는 `var` 로 선언한 필드에 한정해서 사용가능하다.<br>

<br>

### 커스텀 getter

아래는 커스텀 `getter` 를 사용하는 예제

```kotlin
class TickerMeta2(
    val ticker: String,
    var price: BigDecimal = BigDecimal.valueOf(0),
){
    val zoneId: ZoneId
        get() = ZoneId.of("America/New_York")
}

fun main(){
    val t3 = TickerMeta2(ticker = "AAPL", price = BigDecimal.valueOf(170))
    println("t3.ticker = ${t3.ticker} , t3.price = ${t3.price} , t3.zoneId = ${t3.zoneId}")
    println()
}
```

<br>

출력결과

```plain
t3.ticker = AAPL , t3.price = 170 , t3.zoneId = America/New_York
```

<br>

커스텀 getter 는 아래와 같이 일반 함수 구문처럼도 사용할 수 있다.<br>

예제로 학습을 하면서 리마인드 가능하도록 하기 위해 조금 웃긴 예제를 들어봤다.<br>

```kotlin
class TickerMeta3(
    val ticker: String,
    var price: BigDecimal = BigDecimal.valueOf(0),
){
    val zoneId: ZoneId
        get() : ZoneId{
            println("커스텀 getter 에용 애용")
            return ZoneId.of("America/New_York")
        }
}

fun main(){
    val t4 = TickerMeta3(ticker = "AAPL", price = BigDecimal.valueOf(173))
    println("t4.ticker = ${t4.ticker} , t4.price = ${t4.price} , t4.zoneId = ${t4.zoneId}")
    println()
}
```

<br>

출력결과는 아래와 같다<br>

```plain
커스텀 getter 에용 애용
t4.ticker = AAPL , t4.price = 173 , t4.zoneId = America/New_York
```

<br>

### 커스텀 setter

`var` 로 선언한 필드는 커스텀 `setter` 를 적용할 수 있다.

```kotlin
class TickerMeta4(
    val ticker: String,
    val price: BigDecimal = BigDecimal.valueOf(0),
){
    var zoneId: ZoneId = ZoneId.of("America/New_York")
        get() = field
        set(value) { // 커스텀 setter
            if(value != ZoneId.of("America/New_York")){
                field = value
            }
        }
}

fun main(){
    val t5 = TickerMeta4(ticker = "AAPL", price = BigDecimal.valueOf(175))
    t5.zoneId = ZoneId.of("Asia/Seoul")
    println("t5.ticker = ${t5.ticker} , t5.price = ${t5.price} , t5.zoneId = ${t5.zoneId}")
    println()
}
```

<br>

출력결과

```plain
setter 안에 들어왔어용
t5.ticker = AAPL , t5.price = 175 , t5.zoneId = Asia/Seoul
```

<br>

# 3. Backing Field

코틀린은 `getter`, `setter` 에서 `field` 라는 식별자로 필드의 참조를 접근한다. 이것을 `backing field` 라고 부른다.<br>

코틀린에서 프로퍼티에 값을 할당할때 실제로는 `setter` 를 사용한다. 그런데, 이때 무한 재귀가 발생하게 되어 `StackOverFlow` 가 발생할 가능성이 생기게 된다.<br>

예를 들면 아래의 경우에 무한 재귀로 setter 를 호출하게 되어 `StackOverFlow` 가 발생할 수 있다.

```kotlin
class TickerMeta4(
    val ticker: String,
    val price: BigDecimal = BigDecimal.valueOf(0),
){
    var zoneId: ZoneId = ZoneId.of("America/New_York")
        get() = field
        set(value) { // 커스텀 setter
            println("setter 안에 들어왔어용")
            if(value != ZoneId.of("America/New_York")){
                zoneId = value
            }
        }
}
```

<br>

위의 코드에서 `StackOverFlow` 가 발생하는 부분만 발췌해보면 아래와 같다.<br>

```kotlin
var zoneId: ZoneId = ZoneId.of("America/New_York")
    get() = field
    set(value) { // 커스텀 setter
        println("setter 안에 들어왔어용")
        if(value != ZoneId.of("America/New_York")){
            zoneId = value
        }
}
```

set 메서드 내에서 zoneId를 접근하고 있는데, 이렇게 되면 또 다시 setter 내로 들어가서 zoneId를 세팅하려고 하는데 또 다시 setter로 들어가고... 이런 무한 재귀상황이 발생해서 `StackOverFlow` 가 발생하게 된다.<br>

<br>

이런 이유로 setter, getter 내에서는 `field` 라는 식별자를 통해 내부 참조를 명시한다. 위의 코드를 올바르게 고쳐보면 아래와 같다.

```kotlin
var zoneId: ZoneId = ZoneId.of("America/New_York")
    get() = field
    set(value) { // 커스텀 setter
        println("setter 안에 들어왔어용")
        if(value != ZoneId.of("America/New_York")){
            field = value
        }
}
```

<br>

# 코틀린의 프로퍼티는 객체지향적이다.

기본적으로 객체 지향에서 객체의 상태는 프로퍼티로 표현하고, 행위는 메서드로 표현한다.<br>

java 의 경우 필드의 상태를 확인할 때 메서드를 통해 확인한다. 행위 역시 메서드를 이용해 표현한다.<br>

<br>

java 의 경우 아래와 같은 클래스가 있다고 해보자.

```java
public class TickerMeta{
    private String ticker;
    private BigDecimal price;
    private boolean bigTechFlag;
    
    // ...
    
    public boolean isBigTech(){
        return this.bigTechFlag;
    }
    
    public void setIsBigTech(boolean bigTechFlag){
        this.bigTechFlag = bigTechFlag;
    }
}
```

<br>

프로퍼티가 상태를 표현하는 것이 아니라 메서드로 상태를 표현하고 있다.<br>

코틀린은 프로퍼티를 이용해 상태를 표현하는 것이 가능하다.<br>

```kotlin
class TickerMeta4(
    val ticker: String,
    val price: BigDecimal = BigDecimal.valueOf(0),
    var isBigTechFlag : Boolean = false,
){
    var zoneId: ZoneId = ZoneId.of("America/New_York")
        get() = field
        set(value) { // 커스텀 setter
            println("setter 안에 들어왔어용")
            if(value != ZoneId.of("America/New_York")){
                zoneId = value
            }
        }
}
```



