package io.testprj.kopring_webflux.book.application.booknamechecker

import io.testprj.kopring_webflux.book.application.BookNameChecker
import io.testprj.kopring_webflux.global.exception.ThatIsNotABookException
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

class CheckIfBookTest {

    val bookNameChecker : BookNameChecker = BookNameChecker()

    @Test
    fun `'보라빛향기' 가 인자값으로 들어오면, ThatIsNotABookException 이 발생해야 한다`(){
        Assertions
            .assertThatThrownBy { bookNameChecker.checkIfBook("보라빛향기") }
            .isInstanceOf(ThatIsNotABookException::class.java)
    }

}