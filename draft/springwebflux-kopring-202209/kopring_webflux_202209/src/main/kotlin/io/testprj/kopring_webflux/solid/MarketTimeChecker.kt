package io.testprj.kopring_webflux.solid

import java.time.ZonedDateTime

interface MarketTimeChecker {

    fun isMarketTime(zonedDateTime : ZonedDateTime) : Boolean

}