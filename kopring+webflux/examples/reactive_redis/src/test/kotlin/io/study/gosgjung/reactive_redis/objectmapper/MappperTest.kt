package io.study.gosgjung.reactive_redis.objectmapper

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinFeature
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import io.study.gosgjung.reactive_redis.study.StockDto
import org.junit.jupiter.api.Test
import org.slf4j.LoggerFactory
import java.math.BigDecimal

class MappperTest {

    val logger = LoggerFactory.getLogger(javaClass)

    @Test
    fun test1(){
        // KotlinModule 은 Deprecated 되어있다.
        val mapper1 = ObjectMapper().registerModule(KotlinModule())
        val str1 = mapper1.writeValueAsString(StockDto(ticker = "MSFT", lastPrice = BigDecimal.valueOf(240.00)))
        val data1 = mapper1.readValue<StockDto>(str1, StockDto::class.java)
        logger.info("\ndata = $data1 \n")
    }

    @Test
    fun test2(){
        val mapper2 = jacksonObjectMapper()
        val str2 = mapper2.writeValueAsString(StockDto(ticker = "MSFT", lastPrice = BigDecimal.valueOf(240.00)))
        val data2 = mapper2.readValue<StockDto>(str2, StockDto::class.java)
        logger.info("\ndata = $data2 \n")
    }

    @Test
    fun test3(){
        val mapper3 = ObjectMapper().registerKotlinModule()
        val str3 = mapper3.writeValueAsString(StockDto(ticker = "MSFT", lastPrice = BigDecimal.valueOf(240.00)))
        val data3 = mapper3.readValue<StockDto>(str3, StockDto::class.java)
        logger.info("\ndata = $data3 \n")
    }

    @Test
    fun test4(){
        val mapper4 = ObjectMapper().registerKotlinModule()
        val str4 = mapper4.writeValueAsString(StockDto())
        val data4 = mapper4.readValue<StockDto>(str4, StockDto::class.java)
        logger.info("\ndata = $data4")
    }


    @Test
    fun test5(){
        val mapper5 = ObjectMapper().registerModule(KotlinModule(nullIsSameAsDefault = true))
        val str5 = mapper5.writeValueAsString(StockDto())
        val data5 = mapper5.readValue<StockDto>(str5, StockDto::class.java)
        logger.info("\ndata = $data5")
    }

    @Test
    fun test6(){
        val kotlinModule = KotlinModule.Builder()
            .withReflectionCacheSize(512)
            .configure(KotlinFeature.NullToEmptyCollection, false)
            .configure(KotlinFeature.NullToEmptyMap, false)
            .configure(KotlinFeature.NullIsSameAsDefault, enabled = true)
            .configure(KotlinFeature.StrictNullChecks, false)
            .build()

        val mapper6 = ObjectMapper().registerModule(kotlinModule)
        val str6 = mapper6.writeValueAsString(StockDto())
        val data6 = mapper6.readValue<StockDto>(str6, StockDto::class.java)
        logger.info("\ndata = $data6")
    }


}