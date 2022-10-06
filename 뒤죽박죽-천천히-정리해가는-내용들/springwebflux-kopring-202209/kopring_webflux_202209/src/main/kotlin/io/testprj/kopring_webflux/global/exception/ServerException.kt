package io.testprj.kopring_webflux.global.exception

import io.testprj.kopring_webflux.book.code.BookErrorCode

sealed class ServerException(
    val code : Int,
    override val message: String,
) : RuntimeException(message)
data class DuplicatedBookIdException(
    override val message: String = BookErrorCode.DUPLICATED_BOOK_ID.errorMsg
) : ServerException(BookErrorCode.DUPLICATED_BOOK_ID.errorCode, message)

data class NoSuchBookIdExistException(
    override val message: String = BookErrorCode.NO_SUCH_BOOK_ID_EXIST.errorMsg
) : ServerException(BookErrorCode.NO_SUCH_BOOK_ID_EXIST.errorCode, message)

data class EmptyBookIdException (
    override val message: String = BookErrorCode.EMPTY_BOOK_ID.errorMsg
) : ServerException(BookErrorCode.EMPTY_BOOK_ID.errorCode,message)

data class DataInsertException (
    override val message: String = "데이터 생성에 실패했습니다."
) : ServerException(11111, message)