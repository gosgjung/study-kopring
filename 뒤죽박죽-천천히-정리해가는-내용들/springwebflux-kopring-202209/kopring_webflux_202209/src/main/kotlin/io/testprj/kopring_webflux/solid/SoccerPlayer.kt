package io.testprj.kopring_webflux.solid

import java.math.BigDecimal

class SoccerPlayer (
    name: String,
    age: Int,
    salary : BigDecimal,
    teamName : String,
) : Person (name = name, age = age){
}