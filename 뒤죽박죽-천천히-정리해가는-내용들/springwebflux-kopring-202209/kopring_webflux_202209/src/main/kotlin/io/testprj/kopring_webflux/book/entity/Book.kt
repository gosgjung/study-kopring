package io.testprj.kopring_webflux.book.entity

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import java.math.BigDecimal

@Table
data class Book (
    @Id
    val id : Long?,
    @Column
    val name : String,
    @Column
    val price : BigDecimal
){
}