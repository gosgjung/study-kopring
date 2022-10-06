package property

import kotlinx.coroutines.channels.ticker
import java.math.BigDecimal
import java.time.ZoneId

class TickerMeta1(
    val ticker: String,
    var price: BigDecimal
)

class TickerMeta2(
    val ticker: String,
    var price: BigDecimal = BigDecimal.valueOf(0),
){
    val zoneId: ZoneId
        get() = ZoneId.of("America/New_York")
}

class TickerMeta3(
    val ticker: String,
    var price: BigDecimal = BigDecimal.valueOf(0),
){
    val zoneId: ZoneId
        get() : ZoneId{
            println("커스텀 getter 에용 애용")
            return ZoneId.of("America/New_York")
        }
}

class TickerMeta4(
    val ticker: String,
    val price: BigDecimal = BigDecimal.valueOf(0),
){
    var zoneId: ZoneId = ZoneId.of("America/New_York")
        get() = field
        set(value) { // 커스텀 setter
            println("setter 안에 들어왔어용")
            if(value != ZoneId.of("America/New_York")){
                field = value
            }
        }
}

fun main(){
    val t1 = TickerMeta1(ticker = "AAPL", price = BigDecimal.valueOf(20000))
    println("t1.ticker = ${t1.ticker} , t1.price = ${t1.price}")
    println()

    val t2 = TickerMeta1(ticker = "AAPL", price = BigDecimal.valueOf(20000))
    t2.price = BigDecimal.valueOf(30000)
    println("t2.ticker = ${t2.ticker} , t2.price = ${t2.price}")
    println()

    val t3 = TickerMeta2(ticker = "AAPL", price = BigDecimal.valueOf(170))
    println("t3.ticker = ${t3.ticker} , t3.price = ${t3.price} , t3.zoneId = ${t3.zoneId}")
    println()

    val t4 = TickerMeta3(ticker = "AAPL", price = BigDecimal.valueOf(173))
    println("t4.ticker = ${t4.ticker} , t4.price = ${t4.price} , t4.zoneId = ${t4.zoneId}")
    println()
    
    val t5 = TickerMeta4(ticker = "AAPL", price = BigDecimal.valueOf(175))
    t5.zoneId = ZoneId.of("Asia/Seoul")
    println("t5.ticker = ${t5.ticker} , t5.price = ${t5.price} , t5.zoneId = ${t5.zoneId}")
    println()
}
