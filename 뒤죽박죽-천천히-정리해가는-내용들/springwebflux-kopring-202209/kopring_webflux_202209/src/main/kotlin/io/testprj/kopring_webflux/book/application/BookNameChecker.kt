package io.testprj.kopring_webflux.book.application

import io.testprj.kopring_webflux.global.exception.NotExistBookException
import io.testprj.kopring_webflux.global.exception.ThatIsNotABookException
import org.springframework.stereotype.Component

@Component
class BookProcessor {

    fun checkIfBook(bookName: String): Boolean{
        if("광복절특사" == bookName){
            return false
        }
        else if("보라빛향기" == bookName){
            throw ThatIsNotABookException()
        }
        return true
    }

    fun checkIfExist(bookName: String): Boolean{
        if("해변의 카프카" == bookName){
            throw NotExistBookException("재고가 없습니다.")
        }
        else if("심장과 혈관에 힐링을 주는 음악" == bookName){
            throw NotExistBookException(message = "그런 책 없거든요?? ")
        }
        return true
    }

}