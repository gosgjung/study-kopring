package io.testprj.kopring_webflux.config.unit_test

import io.r2dbc.spi.ConnectionFactories
import io.r2dbc.spi.ConnectionFactory
import io.testprj.kopring_webflux.book.repository.BookEntityTemplate
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate
import reactor.core.publisher.Mono
import reactor.test.StepVerifier


@SpringBootTest
class H2ConnectionTest {

    @Autowired
    lateinit var connectionFactory: ConnectionFactory

    @Test
    fun `h2 BookEntityTemplate Drop Table SQL 테스트`(){
        val bookEntityTemplate : BookEntityTemplate = BookEntityTemplate(connectionFactory)

        StepVerifier.create(bookEntityTemplate.dropBookTable())
            .expectNextCount(1)
            .verifyComplete()

        StepVerifier.create(bookEntityTemplate.createBookTable())
            .expectNextCount(1)
            .verifyComplete()
    }


    @Test
    fun `h2 커넥션 테스트`(){
        val connectionFactory: ConnectionFactory =
            ConnectionFactories.get("r2dbc:h2:mem:///sa::testdb?options=DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE")

        val template = R2dbcEntityTemplate(connectionFactory)

        val builder1 = StringBuilder()
        builder1.append("DROP TABLE IF EXISTS book")


        val res1 : Mono<Int> = template.databaseClient
            .sql(builder1.toString())
            .fetch()
            .rowsUpdated()

        StepVerifier.create(res1)
            .expectNextCount(1)
            .verifyComplete()

        val builder = StringBuilder()
        builder.append("create table book ")
        builder.append("( ")
        builder.append("    book_id    bigint auto_increment ")
        builder.append("        primary key, ")
        builder.append("    name        varchar(30)      null, ")
        builder.append("    price        bigint      null ")
        builder.append(")")

        val res2 : Mono<Int> = template.databaseClient
            .sql(
                builder.toString()
            )
            .fetch()
            .rowsUpdated()

        StepVerifier.create(res2)
            .expectNextCount(1)
            .verifyComplete()
    }
}