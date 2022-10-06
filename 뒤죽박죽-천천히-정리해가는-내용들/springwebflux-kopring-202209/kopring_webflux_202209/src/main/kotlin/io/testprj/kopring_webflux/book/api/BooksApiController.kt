package io.testprj.kopring_webflux.book.api

import io.testprj.kopring_webflux.book.application.BookService
import io.testprj.kopring_webflux.book.dto.BooksSearchRequest
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("books")
class BooksApiController (
    val bookService: BookService
){

    @PostMapping("/search")
    fun getBooksSearch(@RequestBody(required = true) booksSearchRequest: BooksSearchRequest){
    }
}