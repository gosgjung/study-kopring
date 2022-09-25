package io.testprj.sgjung.book.entity
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import java.math.BigDecimal

//@Entity
@Table
data class Book(
    @Id
    val bookId: Long,

    @Column
    val price: BigDecimal,

    @Column
    val name: String
)
