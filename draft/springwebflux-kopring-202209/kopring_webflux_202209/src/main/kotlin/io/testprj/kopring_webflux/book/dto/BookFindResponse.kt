package io.testprj.kopring_webflux.book.dto

import java.math.BigDecimal

data class BookFindResponse (
    val id: String,
    val name: String,
    val authorName: String,
    val price: BigDecimal,
    val detail: String
){
}