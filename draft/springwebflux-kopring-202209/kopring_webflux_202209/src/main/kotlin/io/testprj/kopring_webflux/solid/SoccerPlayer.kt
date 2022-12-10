package io.testprj.kopring_webflux.solid

import java.math.BigDecimal

class SoccerPlayer (
    name: String,
    age: Int,
    salary : BigDecimal,
    teamName : String,
) : Person (name = name, age = age) , Interviewable, RetireBehavior{
    override fun interviewing(msg: String) {
        println("인터뷰 내용 >>> $msg")
    }

    override fun retire() {
        println("retire... ")
    }
}