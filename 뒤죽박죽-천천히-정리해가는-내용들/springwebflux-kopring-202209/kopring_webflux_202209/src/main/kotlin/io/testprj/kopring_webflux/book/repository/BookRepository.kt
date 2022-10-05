package io.testprj.kopring_webflux.book.repository

import io.testprj.kopring_webflux.book.entity.Book
import org.springframework.data.repository.reactive.ReactiveCrudRepository

interface BookRepository : ReactiveCrudRepository<Book, String> {
}