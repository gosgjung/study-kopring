package io.testprj.kopring_webflux.global.exception

import io.testprj.kopring_webflux.book.code.BookCategoryErrorCode
import io.testprj.kopring_webflux.book.code.BookErrorCode
import io.testprj.kopring_webflux.global.code.ErrorCode

sealed class ServerException(
    val code : Int,
    override val message: String,
) : RuntimeException(message)

data class ThatIsNotABookException(
    override val message: String = ErrorCode.THAT_IS_NOT_A_BOOK.errorMessage
) : ServerException(ErrorCode.THAT_IS_NOT_A_BOOK.errorCode, message)

data class NoSuchBookExistException(
    override val message: String = ErrorCode.NO_SUCH_BOOK_EXIST.errorMessage
) : ServerException(ErrorCode.NO_SUCH_BOOK_EXIST.errorCode, message)

data class NoSuchCategoryExistException(
    override val message: String = BookCategoryErrorCode.NO_SUCH_CATEGORY_EXISTS.errorMsg
) : ServerException(BookCategoryErrorCode.NO_SUCH_CATEGORY_EXISTS.errorCode, message)

data class DuplicatedBookIdException(
    override val message: String = BookErrorCode.DUPLICATED_BOOK_ID.errorMsg
) : ServerException(BookErrorCode.DUPLICATED_BOOK_ID.errorCode, message)