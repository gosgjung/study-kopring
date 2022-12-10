package io.testprj.kopring_webflux.book.entity

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import java.math.BigDecimal

@Table("book")
data class Book (
    @Id
    val id : String,
    @Column
    val name : String,
    @Column("author_name")
    val authorName : String,
    @Column
    val price : BigDecimal,
    @Column
    val detail : String
){
}