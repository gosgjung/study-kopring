package io.testprj.kopring_webflux.solid

class StockService (
    val stockRepository: StockRepository,
    //***
    // 아래 부분이 문제다. 부분을 KoreaMarketTimeChecker 를 사용하게끔 바꿔줘야 하게 되었다.
    val marketTimeChecker: MarketTimeChecker,
){

    fun processSocketData1(currPrice: CurrPriceDto){
        // 장중 시간이 아닐 경우 process 를 진행하지 않고 리턴
        if(!marketTimeChecker.isMarketTime(currPrice.tradeDateTimeInUTC()))
            return

        // 장중 시간일 경우 데이터 저장
        stockRepository.save(currPrice)
    }

}