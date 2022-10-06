package io.testprj.kopring_webflux.global.code

enum class ErrorCode (
    val errorCode: Int,
    val errorMessage: String,
){
    SUCCESS(20000, "NO_ERROR"),
}