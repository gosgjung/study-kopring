package io.testprj.kopring_webflux.book.application

import io.testprj.kopring_webflux.global.exception.NoSuchBookExistException
import io.testprj.kopring_webflux.global.exception.ThatIsNotABookException
import org.springframework.stereotype.Component

// 단순 예제를 위한 클래스.
// 실무에서 이런 코드가 있지는 않다...
// 예제를 위해 재미를 위해 단순하고 직관적인 예를 위해 작성함.
@Component
class BookNameChecker {

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
            throw NoSuchBookExistException("재고가 없습니다.")
        }
        else if("심장과 혈관에 힐링을 주는 음악" == bookName){
            throw NoSuchBookExistException(message = "그런 책 없거든요?? ")
        }
        return true
    }

}