package io.testprj.kopring_webflux.book.api.bookapicontroller

import io.testprj.kopring_webflux.book.api.BookApiController
import io.testprj.kopring_webflux.book.application.BookNameChecker
import io.testprj.kopring_webflux.config.GlobalExceptionHandlerConfig
import io.testprj.kopring_webflux.global.code.ErrorCode
import io.testprj.kopring_webflux.global.exception.ErrorResponse
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.FilterType
import org.springframework.http.MediaType
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.reactive.server.WebTestClient

@ExtendWith(SpringExtension::class)
@WebFluxTest(controllers = [
    BookApiController::class
], includeFilters = [
    ComponentScan.Filter(
        type = FilterType.ASSIGNABLE_TYPE,
        classes = [BookNameChecker::class, GlobalExceptionHandlerConfig::class]
    )
])
class GetBookTest {

    @Autowired
    lateinit var webTestClient: WebTestClient

    @Test
    fun `'name에 '보라빛향기'가 있다면 예외 발생'`(){
        val body = webTestClient.get()
            .uri {
                it.path("/book")
                    .queryParam("name", "보라빛향기")
                    .build()
            }
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().is2xxSuccessful()
            .expectBody(ErrorResponse::class.java)

        Assertions
            .assertThat(body.returnResult().responseBody?.errorMessage)
            .isEqualTo(ErrorCode.THAT_IS_NOT_A_BOOK.errorMessage)

        println("ErrorResponse >>> ${body.returnResult().responseBody?.errorMessage}")
    }
}