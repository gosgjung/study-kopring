package io.testprj.kopring_webflux.book.code

enum class BookCategoryErrorCode (
    val errorCode: Int,
    val errorMsg: String,
    val errorDetail: String
){
    NO_SUCH_CATEGORY_EXISTS(50001, "NO_SUCH_CATEGORY_EXISTS", "존재하지 않는 카테고리입니다.")
}