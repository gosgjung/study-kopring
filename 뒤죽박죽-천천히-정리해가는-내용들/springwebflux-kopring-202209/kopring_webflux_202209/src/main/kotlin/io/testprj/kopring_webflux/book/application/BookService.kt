package io.testprj.kopring_webflux.book.application

import io.testprj.kopring_webflux.book.dto.*
import io.testprj.kopring_webflux.book.entity.Book
import io.testprj.kopring_webflux.book.repository.BookRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class BookService (
    val bookIdService: BookIdService,
    val bookRepository: BookRepository
){

    fun findById(id: String) : Mono<BookFindResponse> {
        return bookRepository
            .findById(id)
            .mapNotNull {
                it.id?.let { it1 ->
                    BookFindResponse(
                        id = it1,
                        name = it.name,
                        authorName = it.authorName,
                        price = it.price,
                        detail = it.detail
                    )
                }
            }
    }

    fun registerBook(bookRegisterRequest: BookRegisterRequest){
        val book = Book(
            id = bookIdService.nextBookId(bookRegisterRequest.authorName, bookRegisterRequest.name),
            authorName = bookRegisterRequest.authorName,
            name = bookRegisterRequest.name,
            price = bookRegisterRequest.price,
            detail = bookRegisterRequest.detail
        )
        bookRepository.save(book)
    }

    fun modifyBook(bookModifyRequest: BookModifyRequest){
        val book = Book(
            id = bookModifyRequest.id,
            name = bookModifyRequest.name,
            authorName = bookModifyRequest.authorName,
            price = bookModifyRequest.price,
            detail = bookModifyRequest.detail
        )
        bookRepository.save(book)
    }

    fun deleteBook(id : String){
        bookRepository.deleteById(id)
    }


}