package io.testprj.kopring_webflux.book.code

enum class BookErrorCode (
    val errorCode: Int,
    val errorMsg: String,
    val errorDetail: String,
){
    DUPLICATED_BOOK_ID(50000, "DUPLICATED_BOOK_ID", "이미 존재하는 책 ID 입니다.")
}