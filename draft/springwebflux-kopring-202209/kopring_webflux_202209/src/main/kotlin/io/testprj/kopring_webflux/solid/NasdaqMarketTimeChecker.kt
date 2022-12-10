package io.testprj.kopring_webflux.solid

import java.time.ZoneId
import java.time.ZonedDateTime

class NasdaqMarketTimeChecker : MarketTimeChecker{

    override fun isMarketTime(zonedDateTime : ZonedDateTime) : Boolean {
        // 1) 전달된 주식데이터를 미국 현지 시각으로 변환
        val translatedDateTime = zonedDateTime
            .withZoneSameInstant(ZoneId.of("America/New_York"))
            .toLocalDateTime()

        // 2) 주식 데이터가 나스닥 시장 거래 시각에 속하는지 검사
        return (translatedDateTime.toLocalTime().minusSeconds(-5).isAfter(StockMarketTime.US.startTime)
                && translatedDateTime.toLocalTime().plusSeconds(5).isBefore(StockMarketTime.US.endTime))
    }
}