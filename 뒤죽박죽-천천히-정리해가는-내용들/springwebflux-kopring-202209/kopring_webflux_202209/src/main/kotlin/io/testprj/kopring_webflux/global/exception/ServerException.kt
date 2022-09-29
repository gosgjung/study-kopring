package io.testprj.kopring_webflux.global.exception

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
