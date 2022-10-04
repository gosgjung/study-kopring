package io.testprj.kopring_webflux.book.entity

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table

@Table("book_category")
data class BookCategory (
    @Id
    val code : String,
    @Column
    val name : String,
    @Column("label_kr")
    val labelKr : String
){
}