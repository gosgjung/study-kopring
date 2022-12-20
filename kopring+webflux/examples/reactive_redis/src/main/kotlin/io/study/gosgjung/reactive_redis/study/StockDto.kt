package io.study.gosgjung.reactive_redis.study

import com.fasterxml.jackson.databind.annotation.JsonSerialize
import java.math.BigDecimal

data class StockDto(

    val ticker: String = "!!!NOT-ASSIGNED",

//    @JsonProperty("lastPrice")
    @JsonSerialize(using = BigDecimalRounding6Serializer::class)
    val lastPrice: BigDecimal = BigDecimal.valueOf(0.0)

)