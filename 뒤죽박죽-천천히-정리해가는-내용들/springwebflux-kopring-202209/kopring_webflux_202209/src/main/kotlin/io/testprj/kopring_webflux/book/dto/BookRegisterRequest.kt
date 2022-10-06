package io.testprj.kopring_webflux.book.dto

import java.math.BigDecimal

data class BookRegisterRequest (
    val name : String,
    val authorName: String,
    val price: BigDecimal,
    val detail : String
){
}