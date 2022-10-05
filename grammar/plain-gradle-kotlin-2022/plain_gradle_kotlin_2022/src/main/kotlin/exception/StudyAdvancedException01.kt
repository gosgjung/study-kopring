package exception

import java.io.FileWriter

fun main(){
    // 1)
//    use__try_catch_resources()

    // 2)
    runCatching__basic_example()

    //
    runCatching__getOrDefault()

    //
    runCatching__getOrElse()

    // 3)
    runCatching__getOrNull()
}

fun use__try_catch_resources() {
    // try catch resource 와 유사한 역할을 하는 것은 코틀린의 use 라는 확장함수다.
    FileWriter("test.txt1")
        .use {it.write("Hello ...")} // test.txt 라는 파일에 "Hello..." 이라는 문자열이 쓰이게 된다.
}

// 코틀린은 try ~ catch 를 이용한 예외처리 역시 지원하고 있다.
// 코틀린은 try ~ catch 외에도 runCatching 이라는 것을 제공하고 있다..
// runCatching 은 Result 패턴을 구현한 개념이다. 아래는 그 예제
fun runCatching__basic_example(){
    // ## 1) 일반적인 try catch
    val result1 = try{
        hello()
    } catch (e: Exception){
        println(e.message)
        "기본값"
    }
    println("result1 = $result1")
    println()

    // ## 2) runCatching 사용
    val result2 = runCatching { hello() }
        .getOrElse{
            println(it.message)
            "기본값"
        }
    println("runCatching ~ getOrElse{...} = $result2")
    println()
}

fun hello() : String{
    val msg = "hello"
    println("hello() >>> $msg")
    return msg
}

// runCatching ~ getOrNull
// : 실패일 경우 null 을 반환
fun runCatching__getOrNull(){
    val result1 = runCatching { hello() }
        .getOrNull()

    println("runCatching ~ getOrNull() >>> $result1")
}

// runCatching ~ getOrDefault
fun runCatching__getOrDefault(){
    val result1 = runCatching{ hello() }
        .getOrDefault("기본값")

    println("runCatching ~ getOrDefault() >>> $result1")
    println()
}

// runCatching ~ getOrElse
fun runCatching__getOrElse(){
    val result1 = runCatching{ hello() }
        .getOrElse { "기본값" }

    println("runCatching ~ getOrElse() >>> $result1")
    println()
}

// getOrThrow : 성공시엔 값을 반환, 실패시엔 예외를 발생시킨다.
fun runCatching__getOrThrow(){
    val result1 = runCatching{ hello() }
        .getOrThrow()

    println("runCatching ~ getOrThrow() >>> $result1")
}

// 성공인 경우 원하는 값으로 변경한다.
fun runCatching__map__getOrThrow(){
    val result1 = runCatching{ hello() }
        .map{
            it + ", 안녕하십니까~!!"
        }.getOrThrow()

    println(result1)
}

// exceptionOrNull
// 성공인 경우 null 을 반환, 실패인 경우 Throwable 을 반환
fun runCatching__exceptionOrNull(){
    val result1 : Throwable? = runCatching { hello() }
        .exceptionOrNull()

    result1?.let{
        println(it.message)
        throw it
    }
    println()
}


