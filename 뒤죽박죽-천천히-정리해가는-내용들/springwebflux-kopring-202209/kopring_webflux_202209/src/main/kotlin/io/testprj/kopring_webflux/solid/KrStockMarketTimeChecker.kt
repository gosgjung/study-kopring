package io.testprj.kopring_webflux.solid

import java.time.ZoneId
import java.time.ZonedDateTime

class KrStockMarketTimeChecker : MarketTimeChecker{
    // 1) 전달된 주식데이터를 한국 현지 시각으로 변환
    override fun isMarketTime(zonedDateTime: ZonedDateTime): Boolean {
        val translatedDateTime = zonedDateTime
            .withZoneSameInstant(ZoneId.of("Asia/Seoul"))
            .toLocalDateTime()

        return (translatedDateTime.toLocalTime().isAfter(StockMarketTime.KR.startTime)
                && translatedDateTime.toLocalTime().isBefore(StockMarketTime.KR.endTime))
    }
}