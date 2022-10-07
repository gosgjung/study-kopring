# 코틀린의 람다식, SAM 변환

이것 저것 주섬 주섬 정리해봤다.<br>

책이나, 공식문서에서는 `SAM` 변환에 대해서 자바의 interface에 대해서만 동작한다고 한다. 아직도 나는 `SAM` 변환에 대해서 자바 기반의 interface 에만 동작하는 것이라는 것이 잘 이해가 안된다. 공부를 거듭하다보면 조금 납득이 될 듯 하다. <br>

- Kotlin 의 인터페이스에 대해서는 `SAM` 변환이 되지 않는 것인지 혼동이 오는 중

<br>

# 참고자료

- [코틀린을 다루는 기술](https://ridibooks.com/books/754028392?_s=search&_q=%EC%BD%94%ED%8B%80%EB%A6%B0%EC%9D%84+%EB%8B%A4%EB%A3%A8%EB%8A%94+%EA%B8%B0%EC%88%A0&_rdt_sid=search&_rdt_idx=0)

<br>

# `java` 의 람다

자바에서는 람다가 익명의 함수다. `Runnable` 객체를 예로 들어보면 이해가 쉽다.<br>

아래 코드는 Runnable 객체를 람다로 선언하지 않고 그냥 사용할 경우의 코드다.

```java
Runnable r = new Runnable() {
    @Override
    public void run() {
        System.out.println("안녕하세용");
    }
};
```

<br>

위와 같이 작성된 `Runnable` 코드는 아래와 같이 람다식으로 치환이 가능하다.

사실, 람다식은 인터페이스 내의 메서드 하나에 매핑된다.<br>

(단, 해당 인터페이스가 메서드 하나만을 가진 인터페이스여야 한다.)<br>

```java
Runnable r2 = () -> System.out.println("안녕하세용");
```

<br>

`Runnable r2 = () -> System.out.println("안녕하세용");` 을 보면, `public void run () {...}`  에 해당하는 부분이 람다식으로 대체된 것을 볼 수 있다.<br>

<br>

`public void run(){...}` 과 같은 함수가 이름 없이 `() -> {...}` 으로 쓰였다. 이렇게 이름 없는 함수 바디처럼 사용되었다고 해서 흔히 람다구문을 익명함수라고 이야기한다. ([코틀린을 다루는 기술 2.7.5 절](https://ridibooks.com/books/754028392?_s=search&_q=%EC%BD%94%ED%8B%80%EB%A6%B0%EC%9D%84+%EB%8B%A4%EB%A3%A8%EB%8A%94+%EA%B8%B0%EC%88%A0&_rdt_sid=search&_rdt_idx=0))<br>

<br>

자바의 람다가 뭔지 요약을 했으니, 코틀린의 람다식을 정리하면서 죄책감이 안들것 같다.<br>

<br>

# `kotlin` 의 람다

코틀린의 람다 구문은 자바와는 조금 형식이 조금 다르다. 아래 예제를 보자.<br>

```kotlin
fun triple(list: List<Int>): List<Int> = list.map({ a -> a*3 })
```

<br>

코틀린에서는 위의 map 함수 내의 식에서 쓰인 것처럼 중괄호 `{ ... }` 안에 람다식을 표현한다.<br>

위의 예제에서는 map() 함수의 `( ... )` 안에 람다`{ ... }` 을 두어서 식을 정의했는데, 아래에서처럼 `( ... )` 없이 `{ ... }` 만으로 람다식을 표현 가능하다.<br>

- `fun triple (list: List<Int>) : List<Int> = list.map { a -> a*3 }`
  - map 함수에서는 람다는 마지막 인자다. 그리고 유일한 인자다. 이럴 경우 괄호`(...)` 없이 람다`{...}`만 사용할 수 있다.
- `fun product (list: List<Int>)  :  Int = list.fold(1) { a, b -> a*b } `
  - 람다의 파라미터 주변에는 괄호를 치지 않는다. `a,b -> a*b` 를 보면 람다의 파라미터 주변에는 괄호를 치지 않는다는 사실을 알 수 있다.
  - 만약 여기에 괄호를 두어서 사용하면 선언문이 완전히 달라지게 된다.
- `fun List<Int>.product(): Int = this.fold(1) {(a,b) -> a*b}` 
  - 이 코드는 컴파일이 안된다.
  - `(a,b)` 이렇게 괄호로 묶어서 사용하고 있다. 코틀린에서는 이렇게 표현하는 것이 구조분해 할당 표현식이기에 람다식으로 사용할 수 없다.
  - 대신 `a,b -> a*b` 로 표현한다.

<br>

**람다 표현식을 두줄로 표현해야 할 경우**<br>

만약, 아래의 구문을 실행할 때 중간에 total, num 값을 println()으로 찍어보고 싶은데, 이렇게 하면 두줄로 작성해야 한다.

```kotlin
val list1 = listOf(1,2,3)

val r2 = list1.fold(0){ total, num -> total + num }

println("r2 = $r2")

// 출력결과
r1 = 6
```

<br>

total, num 값을 println() 으로 찍어보기 위해 두줄로 작성해야 하는 경우, 아래와 같이 작성할 수 있다.

```kotlin
val list1 = listOf(1,2,3)

val r22 = list1.fold(0){ total, num ->
	println("total = $total , num = $num")
	total + num
}

println("rr2 = $r22")

// 출력결과
rr2 = 6
```

코틀린에서는 람다 바디에 return 을 사용하지 않는다. 나도 처음 배울때 꽤 혼동됐던 문법이었다.<br>

return 을 하는 대신 가장 마지막에 반환할 값을 적어주면, 람다 표현식의 시그니처(함수의 반환 형, 인자값의 조합)에 따라서 반환형을 맞춰서 반환할 지를 코틀린 컴파일러가 추론하는 듯 하다. 위의 식에서 만약 fold 내의 람다가 리턴이 void 였다면 마지막의 total+num 구문은 변수에 따로 값을 담아야 제대로 실행된다.<br>

<br>

https://blog.leocat.kr/notes/2020/03/09/kotlin-reduce-and-fold

<br>

# 람다의 파라미터 타입

코틀린은 람다의 파라미터 타입을 추론한다. 따라서 람다식에 타입을 명시하지 않고 변수만 사용할 수 있다. 타입을 추론하는 것은 조금 단점이 있다.<br>

타입을 추론할 때 오래 걸릴 경우 코틀린 컴파일러는 타입 추론을 포기한다. 그리고 타입 추론을 프로그래머가 직접 해야 한다.<br>

```kotlin
fun List<Int>.product(): Int = this.fold(1){ a:Int, b:Int -> a*b }
```

<br>

타입 지정을 피하고 타입 추론에 더 의존할 수도 있겠지만, 타입을 지정할 경우에 컴파일러에게 명확히 타입을 알려줄 수 있다. 반면, 타입 지정을 안하고 람다식을 사용할 때, 간혹가다 한번씩 컴파일 타임에 타입 추론이 되지 않아 오류를 낼 수도 있게 된다.<br>

<br>

# 여러 줄 람다

람다는 아래 예제처럼 여러 줄에 걸쳐서 작성할 수 있다. 나는 코틀린을 처음 배울때 이 문법을 몰라서 헤맸었다. 람다는 여러줄로 나누서 작성할 수 있는데, 아래와 같이 작성하면 된다.<br>

```kotlin
fun List<Int>.product(): Int = this.fold(1) {
    a, b -> 
    	val result = a*b
    	result
}
```

`return` 키워드를 람다 안에 써도 되지만 생략해도 안전하다.(직접 해보니 확장함수에만 해당)<br>

<br>

# `it` : 람다를 위한 간이 구문

코틀린은 파라미터가 단 하나 뿐인 람다를 편하게 쓸 수 있는 간이 구문을 제공한다. 간이 구문에서는 유일한 파라미터의 이름을 `it` 로 부른다.<br>

앞에서 본 `map` 코드를 이 간이 구문으로 다시 쓰면 아래와 같다.<br>

```kotlin
fun triple(list: List<Int>): List<Int> = list.map {it*3}
```

<br>

위와 같이 한줄로도 작성할 수 있지만, 람다가 여러개 있을 때 내포된 람다가 있을 때 it 가 가리키는 대상을 추측하기 어려워질 수 있다.<br>

따라서 아래 코드들 처럼 람다를 여러줄에 걸쳐서 쓰는 것이 좋은 습관이다.<br>

<br>

**`it` 파라미터로 람다를 정의**

```kotlin
fun triple(list: List<Int>): List<Int> = list.map{
    it*3
}
```

<br>

**일반 람다로 표현** 

```kotlin
fun triple(list: List<Int>): List<Int> = list.map{ a ->
	a * 3
}
```

<br>

# 람다식 단순 예제

두 수를 더하는 add(x, y) 를 예로 들어보자.

```kotlin
fun add(x: Int, y: Int): Int {
    return x + y
}
```

<br>

위의 코드는 람다식으로 표현하면 아래와 같이 표현할 수 있다.<br>

```kotlin
fun add(x: Int, y: Int) = x + y
```

<br>

그리고 아래와 같은 형식으로도 표현할 수 있다.<br>

```kotlin
// {인수1: 타입1, 인수2: 타입2 -> 본문}
var add = {x: Int, y: Int -> x + y}

// 7이 출력된다.
println(add(2,5)) 
```

<br>

위에서 정리했듯, 람다식은 `{...}` 으로 표현한다.

- `{ 인자1, 인자2, ... -> 리턴할 값 또는 계산식 }`
  - ex) {인자 1, 인자2, ... -> 인자1 + 인자2}

<br>

# SAM 변환

내부적으로 람다를 익명의 객체로 변환할 때 SAM 변환이라는 것을 거치는데, 이 SAM이라는 것이 어떤 것인지 정리해봤다.<br>

- Single Abstract Method 변환
- SAM 변환은 자바에서 작성한 인터페이스일 경우에만 동작한다.
- 코틀린에서는 인터페이스 대신에 함수를 사용한다.

<br>

자바에서는 메서드를 하나만 가진 이터페이스를 구현할 때 람다기반의 함수를 구현한다.<br>

코틀린에서는 추상 메서드를 하나를 인수로 사용할 때 람다처럼 함수를 인자로 전달할 수 있다.<br>

함수형 프로그래밍을 예로 들 때 이벤트 리스너를 예로 드는 경우가 많은 것 같다.<br>

<br>

**1) 인터페이스 타입을 인라인으로 구현**

예를 들어 아래처럼 button 객체에 대한 click 이벤트 리스너를 구현하는 코드는 아래와 같다.

```kotlin
button.setOnClickListener(object: View.OnClickListener{
    override fun onClick(v: View?){
        // 클릭 시 처리
    }
})
```

<br>

**2) 인터페이스 타입을 람다로 구현**

위의 코드는 아래와 같이 수정할 수 있다.

```kotlin
button.setOnClickListener({v: View? -> 
	// 클릭시의 동작을 정의
})
```

<br>

**3) 자료형을 생략한 람다식으로 구현**

인자값의 자료형 추론이 가능하면 아래 처럼 자료형을 생략한 람다식으로 정의할 수 있다.

```kotlin
button.setOnClickListener{v -> 
	// 클릭 시 처리
}
```

<br>
**4) `it` 를 인수로 해서 구현**<br>

`it` 를 인수로 해서 사용하는 것도 가능하다.<br>

람다식에서 인수가 하나인 경우에는 인수를 아예 생략하고 람다 블록 내에서 인수를 `it` 로 접근할 수 있다.<br>

아래 코드에서 `it` 는 `View?`  타입의 인수 v 를 의미한다.

```kotlin
button.setOnClickListener {
    it.visibility = View.GONE
}
```

<br>