package io.testprj.kopring_webflux.book.api

import io.testprj.kopring_webflux.book.application.BookNameChecker
import io.testprj.kopring_webflux.global.response.ApiResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("book")
class BookApiController (
    val bookNameChecker: BookNameChecker
){

    @GetMapping("/test")
    fun getTest() : String{
        return "안녕하세요"
    }

    @GetMapping("")
    fun getBookName(@RequestParam(defaultValue = "") name: String) : ApiResponse<Boolean> {
        return ApiResponse(
            message = "정상요청입니다",
            body = bookNameChecker.checkIfBook(name),
            description = "설명..."
        )
    }
}