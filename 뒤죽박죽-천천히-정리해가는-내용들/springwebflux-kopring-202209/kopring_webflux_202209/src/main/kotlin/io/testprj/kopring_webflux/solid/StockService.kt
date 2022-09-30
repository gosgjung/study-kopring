package io.testprj.kopring_webflux.solid

import org.springframework.stereotype.Service

@Service
class StockService (
    val stockRepository: StockRepository,
    val marketTimeChecker: NasdaqMarketTimeChecker
){

    fun processSocketData1(currPrice: CurrPriceDto){
        // 장중 시간이 아닐 경우 process 를 진행하지 않고 리턴
        if(!marketTimeChecker.isMarketTime(currPrice.tradeDateTimeInUTC()))
            return

        // 장중 시간일 경우 데이터 저장
        stockRepository.save(currPrice)
    }

}