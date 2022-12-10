package io.testprj.kopring_webflux.solid

open class Person (
    val name : String,
    val age : Int,
){
    fun letMeIntroduce(person : Person){
        println("안녕하세요. 저는 ${person.name} 이구요. 나이는 ${person.age}세 입니다~")
    }
}