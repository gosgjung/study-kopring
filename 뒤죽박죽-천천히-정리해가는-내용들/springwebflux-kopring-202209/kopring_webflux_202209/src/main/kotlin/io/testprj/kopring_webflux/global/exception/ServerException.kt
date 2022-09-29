package io.testprj.kopring_webflux.global.exception

sealed class ServerException(
    val code : Int,
    override val message: String,
) : RuntimeException(message)

data class ThatIsNotABookException(
    override val message: String = "책이 아니잖아요!!"
) : ServerException(40010, message)

data class NotExistBookException(
    override val message: String = "존재하지 않는 책입니다요"
) : ServerException(40010, message)
