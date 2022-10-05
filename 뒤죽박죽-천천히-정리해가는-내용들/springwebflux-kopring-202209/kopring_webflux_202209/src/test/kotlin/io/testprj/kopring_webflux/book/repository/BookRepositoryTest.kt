package io.testprj.kopring_webflux.book.repository

import io.testprj.kopring_webflux.book.application.BookIdService
import io.testprj.kopring_webflux.book.entity.Book
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentMatchers
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.junit.jupiter.SpringExtension
import reactor.core.publisher.Mono
import reactor.core.scheduler.Schedulers
import reactor.test.StepVerifier
import java.math.BigDecimal


//@DataR2dbcTest(excludeFilters = [ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = [R2dbcAutoConfiguration::class])])
//@DataR2dbcTest(excludeAutoConfiguration = [R2dbcAutoConfiguration::class])
@SpringBootTest
@ExtendWith(SpringExtension::class)
class BookRepositoryTest {

    @Autowired
    lateinit var bookRepository: BookRepository

    @Autowired
    lateinit var bookEntityTemplate: BookEntityTemplate

    @MockBean
    lateinit var bookIdService : BookIdService

    @BeforeEach
    fun `before each`(){
        Mono.fromCallable { bookEntityTemplate.deleteAll() }
            .subscribeOn(Schedulers.boundedElastic())
//        bookEntityTemplate.deleteAll()
    }

    @Test
    fun `INSERT TEST`(){
        // insert 연산의 경우 자체 식별자를 사용할 경우는 id 값을 가진채로 INSERT 를 하기에 save() 시에 UPDATE 하게 된다.
        // 따라서 insert 해야 할 경우 entityTemplate 을 사용

//        val mockIdService = Mockito.mock(BookIdService::class.java)
        Mockito.`when`(bookIdService.nextBookId(ArgumentMatchers.anyString(), ArgumentMatchers.anyString()))
            .thenReturn("멈추지 않는 도전###박지성###2022-10-05T12:21:00:000.+0000")

        val create1 = bookEntityTemplate.insert(
            Book(
                id = bookIdService.nextBookId(authorName = "박지성", bookName = "멈추지 않는 도전"),
                name = "멈추지 않는 도전",
                authorName = "박지성",
                price = BigDecimal.valueOf(13500),
                detail = "자서전"
            )
        )

        StepVerifier.create(create1)
            .assertNext { println(it) }
            .verifyComplete()

    }

    @Test
    fun `UPDATE TEST`(){
        val insertName : String = "박지성"
        val givenKey = "멈추지 않는 도전###박지성###2022-10-05T12:21:00:000.+0000"

        val result = bookEntityTemplate.insert(
            Book(
                id = givenKey,
                name = "멈추지 않는 도전",
                authorName = insertName,
                price = BigDecimal.valueOf(13500),
                detail = "자서전"
            )
        ).flatMap {
            val b1 = Book(
                id = it.id,
                name = it.name,
                authorName = it.authorName + it.authorName,
                price = it.price,
                detail = it.detail
            )
            bookRepository.save(b1)
        }

        StepVerifier.create(result)
            .assertNext { book -> {
                println(book)
                Assertions.assertThat(book.name).isEqualTo(insertName + insertName)
            } }
            .verifyComplete()
    }

    @Test
    fun `FIND BY ID TEST`(){
        val insertName : String = "박지성"
        val bookName : String = "멈추지 않는 도전"
        val givenKey = "멈추지 않는 도전###박지성###2022-10-05T12:21:00:000.+0000"

        val result = bookEntityTemplate.insert(
            Book(
                id = givenKey,
                name = "멈추지 않는 도전",
                authorName = insertName,
                price = BigDecimal.valueOf(13500),
                detail = "자서전"
            )
        ).flatMap {
            bookRepository.findById(it.id)
        }

        StepVerifier.create(result)
            .assertNext{book -> {
                println(book)
                Assertions.assertThat(book.name).isEqualTo(bookName)
            } }
    }

}