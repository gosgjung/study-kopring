package io.testprj.kopring_webflux.book.code

enum class BookErrorCode (
    val errorCode: Int,
    val errorMsg: String,
    val errorDetail: String,
){
    DUPLICATED_BOOK_ID(50000, "DUPLICATED_BOOK_ID", "이미 존재하는 책 ID 입니다."),
    EMPTY_BOOK_ID(40001, "EMPTY_BOOK_ID", "ID가 비어있습니다."),
    NO_SUCH_BOOK_ID_EXIST(50001, "NO_SUCH_BOOK_ID_EXIST", "ID에 해당하는 책이 존재하지 않습니다."),
}