package io.testprj.kopring_webflux.solid

import java.time.LocalTime

enum class StockMarketTime (
    val startTime : LocalTime,
    val endTime : LocalTime,
    val summerStartTime : LocalTime,
    val summerEndTime : LocalTime
){
    US(
        LocalTime.of(9,30,0),
        LocalTime.of(16, 0, 0),
        LocalTime.of(8, 30, 0),
        LocalTime.of(15,0,0)
    ),
    KR(
        LocalTime.of(9, 0, 0),
        LocalTime.of(15,30,0),
        LocalTime.of(9, 0, 0),
        LocalTime.of(15, 30, 0)
    )
}