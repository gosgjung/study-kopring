package io.testprj.kopring_webflux.book.api

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class BookApiController {

    @GetMapping("/book/test")
    fun getTest() : String{
        return "안녕하세요"
    }
}