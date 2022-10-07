# reduce 와 코틀린의 fold()

reduce 는 누적 값을 계산하기 위해 사용된다.<br>

```kotlin
val r1 = list1.reduce { total, num -> total + num }

println("r1 = $r1")

// 출력결과
r1 = 6
```

<br>

fold(Int) 함수 역시 누적 값을 계산하기 위해 사용된다.<br>

다른 점은 fold(Int) 내에 전달되는 인자값은 누적 계산을 시작할 초기값을 의미한다.<br>

```kotlin
val list2 = listOf(1,2,3,4,5)

val r3 = list2.fold(10){total, num -> total + num} 
// 1+2+3+4+5 = 15 and + 10 = 25

val r4 = list2.fold(1){total, num -> total * num}
// 1*2*3*4*5 = 120

val r5 = list2.fold(10){total, num -> total * num}
// 1*2*3*4*5 = 120 and * 10 = 120*10 = 1200

println("r3 = $r3")
println("r4 = $r4")
println("r5 = $r5")

// 출력결과
r3 = 25
r4 = 120
r5 = 1200
```

<br>

람다 표현식에 다른 구문도 함께 사용해야해서 두개 이상의 라인으로 람다 표현식을 작성해야 할 때가 있다. 처음 공부할 때 이게 제일 막막했다.<br>

이럴 때는 아래와 같이 작성해야 한다.<br>

주의해야 할 점은 표현식이 아니라 구문이기 때문에, r22 에 대입할때 이미 그 안의 구문들이 실행된다.<br>

```kotlin
val r22 = list1.fold(0){ total, num ->
	println("total = $total , num = $num")
	total + num
}

println("rr2 = $r22")

// 출력결과
total = 0 , num = 1
total = 1 , num = 2
total = 3 , num = 3
rr2 = 6
```

<br>