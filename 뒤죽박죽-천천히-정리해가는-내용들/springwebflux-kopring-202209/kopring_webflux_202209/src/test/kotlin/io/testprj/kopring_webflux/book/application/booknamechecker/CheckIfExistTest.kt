package io.testprj.kopring_webflux.book.application.booknamechecker

import io.testprj.kopring_webflux.book.application.BookNameChecker
import io.testprj.kopring_webflux.global.exception.NoSuchBookExistException
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

class CheckIfExistTest {

    val bookNameChecker : BookNameChecker = BookNameChecker()

    @Test
    fun `'해변의 카프카'가 인자값으로 들어오면, '재고가 없습니다'라는 문구와 함께 NotExistBookException 을 내보내야 한다`(){
        Assertions.assertThatThrownBy { bookNameChecker.checkIfExist("해변의 카프카") }
            .isInstanceOf(NoSuchBookExistException::class.java)
    }

    @Test
    fun `'심장과 혈관에 힐링을 주는 음악'이 인자값으로 들어오면, '그런 책 없거든요?? '하면서 NotExistBookException 을 내보내자`(){
        Assertions.assertThatThrownBy { bookNameChecker.checkIfExist("심장과 혈관에 힐링을 주는 음악")}
            .isInstanceOf(NoSuchBookExistException::class.java)
    }
}