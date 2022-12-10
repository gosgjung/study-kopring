package io.testprj.kopring_webflux.book.dto

import java.math.BigDecimal

data class BooksSearchRequest (
    val filter: List<String>,       // filter
    val name: String,               // 책 이름
    val priceStart: BigDecimal,     // 가격 구간 조회시 저가
    val priceEnd: BigDecimal,       // 가격 구간 조회시 고가
){
}