package io.testprj.kopring_webflux.book.api.booksapicontroller

import io.testprj.kopring_webflux.book.api.BookApiController
import io.testprj.kopring_webflux.book.application.BookIdService
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.FilterType
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.reactive.server.WebTestClient

@ExtendWith(SpringExtension::class)
@WebFluxTest(
    controllers = [BookApiController::class],
    includeFilters = [
        ComponentScan.Filter(
            type = FilterType.ASSIGNABLE_TYPE,
            classes = [BookIdService::class]
        )
    ]
)
class PostBooksSearchTest {

    @Autowired
    lateinit var webTestClient: WebTestClient
}