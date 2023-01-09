# 코틀린의 when, 그리고 패턴매치

이번 내용도 [함수형 길들이기 - 코드의 재사용과 높은 수준의 테스팅을 원한다면](http://www.yes24.com/Product/Goods/17945487) 의 내용을 참고해서 정리하려고 했으나, 패턴 매치 개념 역시도 이 책은 조금 부실했다. 처음 봤던 순수함수나 이런 개념들보다 개념이 한참 부족했다.<br>

그래서 결국 이번 챕터도 [코틀린으로 배우는 함수형 프로그래밍](http://www.yes24.com/Product/Goods/84899008) 의 내용들을 보고 정리 시작<br>

앞으로 [함수형 길들이기 - 코드의 재사용과 높은 수준의 테스팅을 원한다면](http://www.yes24.com/Product/Goods/17945487) 이책은 아무래도 목차만 참고해서 공부 순서를 어떻게 잡을지만 참고해야 할 듯하다.<br>

<br>



# 참고자료

- [함수형 길들이기 - 코드의 재사용과 높은 수준의 테스팅을 원한다면](http://www.yes24.com/Product/Goods/17945487)
- [코틀린으로 배우는 함수형 프로그래밍](http://www.yes24.com/Product/Goods/84899008)

<br>



# 코틀린의 when

코틀린에서 when 문은 if문 처럼 표현식이다. 코틀린의 when 문은 스칼라의 패턴 매칭과 유사한 기능을 한다.<br>

먼저 아래의 예제를 보자.<br>

<br>

**when(x){...} vs when{ predicate1 -> 리턴값/식 ...}**

값을 기반으로  when 절의 분기조건을 정의할 때는 아래 예제처럼 `when(x){...}` 표현식을 사용하고<br>

조건식을 기반으로 when 절 내에서의 분기조건을  정의할 때는 `when{ predicate1 -> 리턴값 predicate2 -> 리턴값 ... else -> 리턴값or식 }` 같은 표현식을 사용한다.<br>

<br>



**when 절의 else**

코틀린의 when 문은 코틀린의 if 문 처럼 표현식이기에 결괏값을 프로퍼티에 할당하기 위해서는 반드시 else 구문을 작성해야 한다.

when 문은 이 외에도 다양한 패턴의 분기조건으로 넣을 수 있다.<br>

**참고로, sealed class 를 활용해서 클래스 패턴 매칭을 할 때에는 else 를 작성하지 않아도 된다.**

<br>



**when 절로 클래스 패턴매칭 (sealed class 활용)**

참고 : [kotlinlang.org/docs/sealed-classes.html](https://kotlinlang.org/docs/sealed-classes.html)<br>

sealed class 를 활용하면 when 표현식과 함께 클래스 패턴 매칭을 할 수 있게 해준다. 이와 관련된 예제는 `e.g.3` 에 정리해두었다.<br>

**참고로, sealed class 를 활용해서 클래스 패턴 매칭을 할 때에는 else 를 작성하지 않아도 된다.**

<br>



아래 예제를 보자.<br>

**e.g.1) 값을 기반으로 분기조건을 정해서 when 절에서 매칭**<br>

```kotlin
when (x) { 							// 1)
    1 -> print("x == 1")
    2,3 -> print("x == 2 or 3")		// 2)
    parseInt("4") -> print("x = 4")
    else -> print("else number")	// 3)
}
```



- 1\) 값을 기반으로 when 절 내에서 조건값들을 비교할 때는 when(x) { ... } 표현식을 사용한다.
- 2\) when 절에서 복수의 값에 대한 조건에 대한 행동을 정의할 때는 when(x) { ... **c1,c2** -> 식 or 값 ... } 과 같은 방식으로 표현한다.
- 3\) when 절의 가장 마지막은 `else` 를 정의해줘야 한다.

<br>



**e.g.2) 조건문을 기반으로 분기 조건을 정해서 when 절에서 매칭**<br>

```kotlin
val numType = when {			// 1)
    x == 0 -> "zero"			// 2)
    x > 0 -> "positive"			// 2)
    else -> "negative"			// 3)
}
```



- 1\) 조건문을 기반으로 분기조건을 정해서 when 절로 매칭을 할 때는 `when { ... }` 표현식을 사용한다.
  - 조건문을 표현식으로 직접 넣을 때는 when(x) 처럼 표현하지 않고 (x)를 생략한다.
- 2\) 표현식을 분기 조건으로 해주었다. 이렇게 표현식을 분기조건으로 사용하면 1\) 에서 정리했듯 `when {...}` 표현식을 사용하다.
- 3\) when 절의 가장 마지막은 `else` 를 정의해줘야 한다.

<br>



**e.g.3) when 절로 클래스 패턴 매칭 (sealed class 활용)**

참고 : [kotlinlang.org/docs/sealed-classes.html](https://kotlinlang.org/docs/sealed-classes.html)

```kotlin
sealed class Expr
data class Const(val number: Double): Expr()
data class Sum(val e1: Expr, val e2: Expr): Expr()
object NotANumber: Expr()

fun eval(expr: Expr): Double = when(expr){
    is Const -> expr.number
    is Sum -> eval(expr.e1) + eval(expr.e2)
    NotANumber -> Double.NaN
    // the `else` clause is not required because we've covered all the cases
}
```

<br>



# 패턴 매칭

값, 조건 타입 등의 패턴에 따라서 매칭되는 동작을 수행하게 하는 기능을 의미.<br>

코틀린에서는 패턴 매칭을 완벽하게 지원하지는 않는다.<br>

(매개변수를 포함하는 타입(e.g. 리스트)이나 함수의 타입에 대한 패턴매칭을지원하지 않는다. 참고로 스칼라, 하스켈에서는 이런 제약이 없이 다양한 패턴 매칭을 지원한다.)<br>

<br>

when 문에 값을 넣어서 패턴 매칭을 정의하는 것이 가능하다. 아래의 예제를 보자.

```kotlin
fun main(args: Array<String>){
    println(checkValue("kotlin"))			// "kotlin" 출력
    println(checkValue(5))					// "1..10" 출력
    println(checkValue(15))					// "11 or 15" 출력
    println(checkValue(User("Joe", 76)))	// "User" 출력
    println(checkValue("unknown"))			// "SomeValue" 출력
}

data class User(val name: String, val age: Int)

fun checkValue(value: Any) = when(value){
    "kotlin" -> "kotlin"		// 1)
    in 1..10 -> "1..10"			// 2)
    11,15 -> "11 or 15"			// 3)
    is User -> "User"			// 4)
    else -> "SomeValue"			// 5)
}
```



1\) "kotlin" -> "kotlin" : value 가 "kotlin" 이라는 문자열이면 "kotlin" 을 반환한다.

2\) in 을 사용하면 값의 범위에 대한 패턴 매칭을 할 수 있다.

3\) ',' 을 사용하면 여러 개의 값을 한번에 매칭할 수 있다.

4\) is 를 사용하면 객체의 타입에 따른 패턴 매칭을 할 수 있다.

5\) else 는 반드시 정의해줘야 한다. (단, sealed class 기반 패턴 매칭을 할 경우는 예외)



<br>

when 문에 값을 넣지 않으면 조건문에 따른 패턴을 정의할 수 있다. 아래는 조건문에 따른 패턴 매칭의 예다.

```kotlin
fun main(){
    println(checkCondition("kotlin"))			// "kotlin" 출력
    println(checkCondition(5))					// "1..10" 출력
    println(checkCondition(User("Joe", 76)))	// "== User(Joe, 76)" 출력
    println(checkCondition(User("Sandy", 65)))	// "is User" 출력
    println(checkCondition("unknown"))			// "SomeValue" 출력
}

data class User(val name: String, val age: Int)

fun checkCondition(value: Any) = when {
    value == "kotlin" -> "kotlin"
    value in 1..10 -> "1..10"
    value == User("Joe", 76) -> "=== User"
    value == User("Joe", 76) -> "== User(Joe, 76)"
    value is User -> "is User"
    else -> "SomeValue"
}
```































