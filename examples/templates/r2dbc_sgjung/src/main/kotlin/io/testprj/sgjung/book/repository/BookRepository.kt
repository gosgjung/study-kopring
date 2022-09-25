package io.testprj.sgjung.book.repository

import io.testprj.sgjung.book.entity.Book
import org.springframework.data.repository.reactive.ReactiveCrudRepository

interface BookRepository : ReactiveCrudRepository<Book, Long>{
}