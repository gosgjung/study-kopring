package io.testprj.sgjung.payment.entity
import java.math.BigDecimal
import javax.persistence.*

@Entity
@Table(schema = "collector")
data class Book(
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    val paymentId: Long,
    @Column
    val accountId: Long,
    @Column
    val amount: BigDecimal,
    @Column
    val methodType: String,
    @Column
    val itemCategory: String,
    @Column
    val region: String
)
