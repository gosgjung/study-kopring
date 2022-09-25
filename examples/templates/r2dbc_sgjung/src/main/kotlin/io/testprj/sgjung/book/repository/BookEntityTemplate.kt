package io.testprj.sgjung.book.repository

import io.testprj.sgjung.book.entity.Book
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate
import org.springframework.data.relational.core.query.Criteria
import org.springframework.data.relational.core.query.Query
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux

@Component
class BookEntityTemplate (
    val r2dbcEntityTemplate : R2dbcEntityTemplate
){
    fun findByName(name: String): Flux<Book> {
        return r2dbcEntityTemplate.select(Book::class.java)
            .matching(Query.query(Criteria.where("name").`is`("스포츠")).limit(5).offset(0))
            .all()
    }
}