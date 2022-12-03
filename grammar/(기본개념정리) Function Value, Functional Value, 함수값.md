# (기본개념정리) Function Value, Functional Value, 함수값

함수형 프로그래밍에 대해 이야기 하는 글에서 `functional value`, `function value` 는 보통 함수가 반환하는 값(return value 나 result)이 아니라 함수인 값을 의미한다.<br>

보통은 함수에 인자값으로 전달하는 람다, 익명함수와 같은 함수 표현식을 `Function Value` 또는 `Functional Value` 라고 부른다.<br>

`함숫값` 이라는 용어가 함수가 리턴하는 값으로 이해되기 쉬워서, 내 경우는 `functional value`, `function value` 라고 이해하기로 했다.<br>

<br>

# 참고자료

- [코틀린 완벽 가이드](http://www.yes24.com/Product/Goods/107698728)

<br>

# 예제

예를 들어 `aggregate()` 라는 함수가 있다고 하자. 아래의 `aggregate()` 함수는 이번 문서의 모든 곳에서 예제로 사용할 함수다.<br>

```kotlin
fun aggregate(numbers: IntArray, op: (Int, Int) -> Int): Int {
    var result = numbers.firstOrNull()
        ?: throw IllegalArgumentException("Empty Array")

    for(i in 1..numbers.lastIndex)
        result = op(result, numbers[i])

    return result
}
```

<br>

## e.g. 익명함수

예를 들어 아래의 함수 정의에서 `fun(result, op) = result + op` 는 `Functional Value`, `Function Value` 로 쓰였다. 

```kotlin
fun sum (numbers: IntArray) = 
	aggregate(numbers, fun(result, op) = result + op)
```

<br>

함수를 인라인으로 정의해서 함수의 파라미터로 전달하고 있다. 함수가 하나의 값으로서 전달됐다.<br>

<br>

# e.g. 람다식

예를 들어 아래의 함수 정의에서 `{result, op -> result + op}` 는 람다식인데, 이 람다식 역시도  `Functional Value`, `Function Value` 로 쓰였다.

```kotlin
fun sum (numbers: IntArra) = 
	aggregate(numbers, {result, op -> result + op})
```

 





