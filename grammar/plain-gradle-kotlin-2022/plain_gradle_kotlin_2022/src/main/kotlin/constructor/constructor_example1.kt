package constructor

import java.math.BigDecimal
import java.time.ZoneId

class TickerMeta1(val ticker: String, val zoneId: ZoneId)

class TickerMeta2(val ticker: String){
    var zoneId: ZoneId = ZoneId.of("America/New_York")
    var earning: BigDecimal = BigDecimal.valueOf(0)

    constructor(ticker: String, zoneId: ZoneId, earning: BigDecimal) : this(ticker){
        this.zoneId = zoneId
        this.earning = earning
    }
}

fun main(){
    val t1 = TickerMeta1(ticker = "AAPL", zoneId = ZoneId.of("America/New_York"))
    println("t1.ticker = ${t1.ticker} , t1.zoneId = ${t1.zoneId}")
    println()

    val t2 = TickerMeta2(ticker = "AAPL", zoneId = ZoneId.of("America/New_York"), earning = BigDecimal.valueOf(30000))
    println("t2.ticker = ${t2.ticker} , t2.zoneId = ${t2.zoneId} , t2.earning = ${t2.earning}")
    println()
}

