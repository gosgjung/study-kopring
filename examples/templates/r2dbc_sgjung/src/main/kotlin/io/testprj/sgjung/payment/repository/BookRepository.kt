package io.testprj.sgjung.payment.repository

import io.testprj.sgjung.payment.entity.Book
import org.springframework.data.repository.reactive.ReactiveCrudRepository

interface BookRepository : ReactiveCrudRepository<Book, Long>{
}