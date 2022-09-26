package io.testprj.kopring_webflux.book.repository

import io.r2dbc.spi.ConnectionFactory
import io.testprj.kopring_webflux.book.entity.Book
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate
import org.springframework.data.relational.core.query.Criteria.where

import org.springframework.data.relational.core.query.Query
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

@Component
class BookEntityTemplate (
    val connectionFactory: ConnectionFactory,
    val r2dbcEntityTemplate: R2dbcEntityTemplate = R2dbcEntityTemplate(connectionFactory)
){
    fun findAll(id : Long) : Mono<Book> {
        return r2dbcEntityTemplate.select(Book::class.java)
            .matching(Query.query(where("id").`is`(1))).one()
    }

    fun dropBookTable(): Mono<Int> {
        val builder = StringBuilder()
        builder.append("DROP TABLE IF EXISTS testprj.book")

        return r2dbcEntityTemplate.databaseClient
            .sql(builder.toString())
            .fetch()
            .rowsUpdated()
    }

    fun createBookTable() : Mono<Int>{
        val builder = StringBuilder()
        builder.append("create table testprj.book ")
        builder.append("( ")
        builder.append("    book_id    bigint auto_increment ")
        builder.append("        primary key, ")
        builder.append("    name        varchar(30)      null, ")
        builder.append("    price        bigint      null ")
        builder.append(")")

        return r2dbcEntityTemplate.databaseClient
            .sql(
                builder.toString()
            )
            .fetch()
            .rowsUpdated()
    }


}