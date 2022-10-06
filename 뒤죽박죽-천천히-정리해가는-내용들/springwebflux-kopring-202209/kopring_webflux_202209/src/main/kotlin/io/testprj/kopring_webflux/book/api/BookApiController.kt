package io.testprj.kopring_webflux.book.api

import io.testprj.kopring_webflux.book.application.BookService
import io.testprj.kopring_webflux.book.dto.BookFindResponse
import io.testprj.kopring_webflux.book.dto.BookModifyRequest
import io.testprj.kopring_webflux.book.dto.BookRegisterRequest
import io.testprj.kopring_webflux.book.dto.BookResponse
import io.testprj.kopring_webflux.global.exception.EmptyBookIdException
import org.springframework.util.StringUtils
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono

@RestController
@RequestMapping("book")
class BookApiController (
    val bookService: BookService
){
    @GetMapping("")
    fun getBook(@RequestParam(defaultValue = "") id: String) : Mono<BookResponse<BookFindResponse>> {
        if(!StringUtils.hasText(id)) throw EmptyBookIdException()
        return bookService.findById(id)
    }

    @PostMapping("")
    fun postBook(@RequestBody(required = true) bookRequest: BookRegisterRequest){
        bookService.registerBook(bookRequest)
    }

    @PutMapping("")
    fun putBook(@RequestBody bookRequest: BookModifyRequest){
        bookService.modifyBook(bookRequest)
    }

    @DeleteMapping("")
    fun deleteBook(@RequestParam(defaultValue = "") id: String){
        if(!StringUtils.hasText(id)) throw EmptyBookIdException()
        bookService.deleteBook(id)
    }

}