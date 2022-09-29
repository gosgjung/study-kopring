package io.testprj.kopring_webflux.global.code

enum class ErrorCode (
    val errorCode: Int,
    val errorMessage: String,
){
    THAT_IS_NOT_A_BOOK(40010, "책이 아닙니다."),
    NO_SUCH_BOOK_EXIST(40010, "존재하지 않는 책입니다.")
}