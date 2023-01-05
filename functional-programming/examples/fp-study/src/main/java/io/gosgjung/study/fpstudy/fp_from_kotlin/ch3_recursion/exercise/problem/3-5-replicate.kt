package io.gosgjung.study.fpstudy.fp_from_kotlin.ch3_recursion.exercise.problem

/**
 * 숫자를 두개 입력 받은 후 두 번째 숫자를 첫 번째 숫자만큼 가지고 있는
 * 리스트를 반환하는 함수를 만들어보자.
 * 예를 들어 replicate(3,5) 를 입력하면 5가 3개 있는 리스트 [5,5,5]를 반환한다.
 */
fun main() {
    require(listOf(5, 5, 5) == replicate(3, 5))
    require(listOf(1, 1, 1, 1, 1) == replicate(5, 1))
}

private fun replicate(n: Int, element: Int): List<Int> = TODO()