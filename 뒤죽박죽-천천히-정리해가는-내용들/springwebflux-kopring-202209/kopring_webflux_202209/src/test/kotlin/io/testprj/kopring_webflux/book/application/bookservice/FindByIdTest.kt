package io.testprj.kopring_webflux.book.application.bookservice

import io.testprj.kopring_webflux.book.repository.BookRepository
import org.springframework.boot.test.mock.mockito.MockBean

class FindByIdTest {

    @MockBean
    lateinit var bookRepository: BookRepository


}