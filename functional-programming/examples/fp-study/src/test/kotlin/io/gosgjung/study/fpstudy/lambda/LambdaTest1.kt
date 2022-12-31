package io.gosgjung.study.fpstudy.lambda

import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

/**
 * 코틀린의 람다 선언법 매번 헷갈린다.
 * 그래서 예제를 정리하기로 결정
 *
 * 정확하게는 람다를 식으로 변수에 할당할때, 변수의 타입까지 선언하는 구문이 헷갈림..
 * 자바 람다보다는 코틀린 람다가 아직은 익숙하지 않다.
 */
class LambdaTest1 {

    @Test
    @DisplayName("덧셈을 하면서 연산결과를 출력하고, 결과값을 리턴")
    fun `덧셈을 하면서 연산결과를 출력하고, 결과값을 리턴`(){
        
        val plusFn1 : (Int, Int) -> Int = {
            left: Int, right: Int -> 
                val result = left + right
                println("result = $result")
                result
        }
        
        assertThat(plusFn1(1,2), Matchers.equalTo(3))
        println("람다 1 호출 결과값 = ${plusFn1(1,2)}")
        println()
        
        val plusFn2 = {
            left: Int, right: Int -> 
                val result = left + right
                println("result = $result")
                result
        }
        
        assertThat(plusFn2(1,2), Matchers.equalTo(3))
        println("람다 2 호출 결과값 = ${plusFn2(1,2)}")
    }

    /**
     * 위의 예제가 도저히 뭔지 모르겠고 처음부터 다시 봐야겠다 싶으면 아래의 자료를 참고
     * https://ddolcat.tistory.com/557
     * https://thdev.tech/kotlin/2018/03/17/Kotlin-Lambdas/
     * https://codechacha.com/ko/kotlin-lambda-expressions/
     */

}