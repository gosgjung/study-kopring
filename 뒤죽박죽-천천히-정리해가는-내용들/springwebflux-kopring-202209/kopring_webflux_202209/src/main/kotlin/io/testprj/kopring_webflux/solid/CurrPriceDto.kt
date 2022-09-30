package io.testprj.kopring_webflux.solid

import java.math.BigDecimal
import java.time.*

data class CurrPriceDto(
    val currPrice : BigDecimal,
    val ticker : String,
    val tradeDate : LocalDate, // UTC 기준 로컬 날짜로 전달
    val tradeTime : LocalTime, // UTC 기준 로컬 시간으로 전달
){
    fun tradeDateTimeInUTC(): ZonedDateTime{
        return ZonedDateTime.of(tradeDate, tradeTime, ZoneId.of("Etc/UTC"))
    }
}
