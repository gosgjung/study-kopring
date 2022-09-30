package io.testprj.kopring_webflux.solid

import org.junit.jupiter.api.Test
import org.mockito.Mockito
import java.math.BigDecimal
import java.time.LocalDate
import java.time.LocalTime

class SolidTest {

    @Test
    fun `단일책임 원칙 테스트`(){
        val stockService = StockService(StockRepository(), NasdaqMarketTimeChecker())

        val currData = CurrPriceDto(
            currPrice = BigDecimal.valueOf(150),
            ticker = "AAPL",
            tradeDate = LocalDate.of(2022,9,30),
            tradeTime = LocalTime.of(13,0,1)
        )

        stockService.processSocketData1(currData)
    }
}