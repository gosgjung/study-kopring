package io.testprj.kopring_webflux.global.response

data class ApiResponse <T>(
    val message : String = "empty Message",
    val body : T,
    val description : String
){
}