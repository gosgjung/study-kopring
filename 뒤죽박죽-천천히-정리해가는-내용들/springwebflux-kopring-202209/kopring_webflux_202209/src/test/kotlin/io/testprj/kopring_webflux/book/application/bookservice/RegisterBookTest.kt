package io.testprj.kopring_webflux.book.application.bookservice

import io.testprj.kopring_webflux.book.application.BookService
import io.testprj.kopring_webflux.book.dto.BookRegisterRequest
import io.testprj.kopring_webflux.book.entity.Book
import io.testprj.kopring_webflux.book.repository.BookEntityTemplate
import io.testprj.kopring_webflux.book.repository.BookRepository
import io.testprj.kopring_webflux.global.exception.DuplicatedBookIdException
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentMatchers
import org.mockito.Mockito
import org.mockito.kotlin.any
import org.mockito.kotlin.whenever
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.test.context.junit.jupiter.SpringExtension
import reactor.test.StepVerifier
import java.math.BigDecimal

@SpringBootTest
@ExtendWith(SpringExtension::class)
class RegisterBookTest {

    @MockBean
    lateinit var bookRepository: BookRepository

    @MockBean
    lateinit var bookEntityTemplate: BookEntityTemplate

    @Autowired
    lateinit var bookService: BookService

    @Test
    fun `중복된 id 에 대한 데이터 insert 시도를 할 경우 DuplicatedBookIdException 이 발생해야 한다`(){
        whenever(bookEntityTemplate.insert(any()))
            .thenThrow(DataIntegrityViolationException::class.java)
//        Mockito.`when`(bookEntityTemplate.insert(ArgumentMatchers.any(Book::class.java)))
//            .thenThrow(DataIntegrityViolationException::class.java)

        val req = BookRegisterRequest(
            name = "asdf",
            authorName = "asdfasdf",
            price = BigDecimal.valueOf(100000),
            detail = "asdfasdf"
        )


        Assertions.assertThatThrownBy {
            val reg = bookService.registerBook(req)

            StepVerifier.create(reg)
                .assertNext {
                    println("it ==>> $it")
                }

        }.isInstanceOf(DuplicatedBookIdException::class.java)

    }

}