# 코틀린의 스코프 함수들 - let, run, with, apply, also

객체의 컨텍스트 내에서 코드 블록을 실행하기 위해 존재하는 함수들 중 `let`, `run`, `with`, `apply` 함수는 스코프함수라고 부른다. 이 `let`, `with`, `run`, `apply` 함수는 모두 코틀린의 표준 라이브러리에 포함된 함수들이다.<br>

스코프 함수들을 유연하게 잘 사용하면, 불필요한 변수 선언들이 없어지고, 코드도 더 간결하고 명확해진다. 스코프 함수의 코드 블록 내에서는 변수명을 사용하지 않아도 객체에 접근할 수 있다. 이 것은 **수신자 객체**에 접근할 수 있기 때문에 접근이 가능한 것이다. <br>

이 **수신자 객체**는 람다식 내부에서 사용할 수 있는 객체의 참조다. 스코프 함수를 사용하면 수신자 객체에 대한 참조로 `this`, `it` 를 사용한다.<br>

<br>

## let, run, with, apply, also

let

- 수신자 객체 : it 로 접근
- 반환 값: 함수의 결과
- 확장함수 **적용가능**

run

- 수신자 객체 : this 로 접근
- 반환 값: 함수의 결과
- 확장함수 **적용가능**

with

- 수신자 객체 : this 로 접근
- 반환 값: 함수의 결과
- 확장함수 **적용 불가**

apply

- 수신자 객체 : this 로 접근
- 반환 값: 컨텍스트 객체
- 확장함수 **적용가능**

also

- 수신자 객체 : it 로 접근
- 반환 값: 컨텍스트 객체
- 확장함수 **적용가능**

<br>

## let

null 이 아닌 경우 적용할 로직을 통해 새로운 결과를 반환하고자 할 때 사용한다.<br>

단적으로 비유를 들면, map 함수를 null 이 아닐때 사용하는 그런 경우를 예로 들 수 있다.<br>

<br>

ex) null 데이터에 let 함수를 적용해서 실행되는 지 확인

```kotlin
fun main() {
    val str: String? = null
    
    str?.let{
        println(it)
    }
    
    // 출력결과
    // 아무것도 출력되지 않는다.
}
```

<br>

ex) null 이 아닌 데이터에 let 함수를 적용했을 때 실행되는지 확인

```kotlin
fun main(){
    val str: Stirng? = "Hello~"
    
    str?.let{
        println(it)
    }
    
    // 출력결과
    // Hello~
}
```

<br>

ex) let 함수를 map 함수처럼 결과를 매핑해주는 역할로 사용

```kotlin
val str1: String? = "대한민국~!!"

val result1 = str1?.let{
    println("it = $it")
    str1.length
}

println("result1 = $result1")
println()

val str2: String? = "hello ~ "

val result2 = str2?.let{
    println("it = $it")
    str2.uppercase()
}

println("result2 = $result2")
```

<br>

출력결과

```plain
it = 대한민국~!!
result1 = 7

it = hello ~ 
result2 = HELLO ~ 
```

<br>

ex) let 이 여러번 중첩되어 다소 복잡한 코드

```kotlin
val str: String? = "대한민국~!!"
val result = str?.let{
    println("it = $it")

    val hello: String? = "hello ~ "
    hello?.let{
        val HELLO: String? = it.uppercase()
        println("str, hello 모두 null 이 아님")
    }

    "world".uppercase()
}

println("result = $result")
```

<br>

출력결과

```plain
it = 대한민국~!!
str, hello 모두 null 이 아님
result = WORLD
```

<br>

ex) let 이 너무 중첩되어 스파게티코드 처럼 된다면, if 식을 사용하면 유용하다.

```kotlin
val str: String? = "대한민국~!!"
val result = str?.let{
    println("it = $it")

    val hello: String? = "hello ~ "
    val HELLO: String? = "HELLO ~ "

    if(!hello.isNullOrBlank() && !HELLO.isNullOrBlank()){
        println("hello, HELLO 모두 null 이 아님")
    }

    "world".uppercase()
}

println("result = $result")
```

<br>

## run

