package io.testprj.kopring_webflux.book.application

import io.testprj.kopring_webflux.book.dto.*
import io.testprj.kopring_webflux.book.entity.Book
import io.testprj.kopring_webflux.book.repository.BookEntityTemplate
import io.testprj.kopring_webflux.book.repository.BookRepository
import io.testprj.kopring_webflux.global.exception.DataInsertException
import io.testprj.kopring_webflux.global.exception.DuplicatedBookIdException
import io.testprj.kopring_webflux.global.exception.NoSuchBookIdExistException
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.switchIfEmpty

@Service
class BookService (
    val bookIdService: BookIdService,
    val bookRepository: BookRepository,
    val bookEntityTemplate: BookEntityTemplate
){

    fun findById(id: String): Mono<BookResponse<BookFindResponse>> {
        return bookRepository
            .findById(id)
            .map{
                val body = BookFindResponse(
                    id = it.id,
                    name = it.name,
                    authorName = it.authorName,
                    price = it.price,
                    detail = it.detail
                )
                BookResponse.okResponse(body, "GOOD")
            }
            .switchIfEmpty { throw NoSuchBookIdExistException() }
    }

    fun registerBook(bookRegisterRequest: BookRegisterRequest): Mono<BookResponse<BookRegisterResponse>> {
        val book = Book(
            id = bookIdService.nextBookId(bookRegisterRequest.authorName, bookRegisterRequest.name),
            authorName = bookRegisterRequest.authorName,
            name = bookRegisterRequest.name,
            price = bookRegisterRequest.price,
            detail = bookRegisterRequest.detail
        )

        return bookEntityTemplate.insert(book)
            .map{
                val body = BookRegisterResponse(
                    id = it.id,
                    name = it.name,
                    authorName = it.authorName,
                    price = it.price,
                    detail = it.detail
                )
                BookResponse.okResponse(body, "OK")
            }
    }

    fun modifyBook(bookModifyRequest: BookModifyRequest): Mono<BookResponse<BookModifyResponse>> {
        val book = Book(
            id = bookModifyRequest.id,
            name = bookModifyRequest.name,
            authorName = bookModifyRequest.authorName,
            price = bookModifyRequest.price,
            detail = bookModifyRequest.detail
        )

        return bookRepository.save(book)
            .map{
                val body = BookModifyResponse(
                    id = it.id,
                    name = it.name,
                    authorName = it.authorName,
                    price = it.price,
                    detail = it.detail
                )
                BookResponse.okResponse(body, "OK")
            }
    }

    fun deleteBook(id : String): Mono<BookResponse<String>> {
        return bookRepository.deleteById(id)
            .map{
                BookResponse.okResponse("", "OK")
            }
    }

}