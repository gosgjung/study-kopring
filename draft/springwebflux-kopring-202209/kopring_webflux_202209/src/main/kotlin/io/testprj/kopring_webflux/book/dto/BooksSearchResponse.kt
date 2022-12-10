package io.testprj.kopring_webflux.book.dto

import java.math.BigDecimal

data class BooksSearchResponse (
    val id: String,
    val name: String,
    val price: BigDecimal,
    val detail: String
){
}