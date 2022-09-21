package io.history.kopring_plain.external.csv.dummystock

// price의 경우 가급적 BigDecimal 을 써야하는 것이 맞지만, 예제용도이기에 그냥 String으로 ...
data class DummyStock (val ticker: String, val price: String){

}
