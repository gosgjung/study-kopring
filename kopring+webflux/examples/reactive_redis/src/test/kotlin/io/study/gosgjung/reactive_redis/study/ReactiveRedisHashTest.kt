package io.study.gosgjung.reactive_redis.study

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.redis.core.ReactiveRedisTemplate
import reactor.test.StepVerifier
import java.math.BigDecimal

@SpringBootTest
class ReactiveRedisHashTest {

    @Autowired
    @Qualifier("playerAgeMap")
    lateinit var playerAgeMap : ReactiveRedisTemplate<String, String>

    @Autowired
    @Qualifier("stockMap")
    lateinit var stockMap : ReactiveRedisTemplate<String, StockDto>

    @Test
    fun `축구국가대표팀의 각 선수들의 나이를 입력해보기`(){
        val hash = playerAgeMap.opsForHash<String, String>()
        val namespace = "KoPlayerHash"
        hash.putIfAbsent(namespace, "이강인", "21").subscribe()
        hash.putIfAbsent(namespace, "이천수", "43").subscribe()
        hash.putIfAbsent(namespace, "김민재", "24").subscribe()

        val msp = buildMap<String,Int>{
            put("이강인",21)
            put("이천수",43)
            put("김민재",24)
        }

        msp.map{
            StepVerifier.create(hash.get(namespace, it.key))
                .expectSubscription()
                .expectNext(it.value.toString())
                .expectComplete()
                .verify()
        }
    }

    @Test
    fun `JacksonSerializer를 테스트해보자`(){
        val hash = stockMap.opsForHash<String, StockDto>()
        val namespace = "USStockHash"
        hash.putIfAbsent(namespace, "MSFT", StockDto(ticker = "MSFT", lastPrice = BigDecimal.valueOf(240.05))).subscribe()
        hash.putIfAbsent(namespace, "AAPL", StockDto(ticker = "AAPL", lastPrice = BigDecimal.valueOf(132.37))).subscribe()
        hash.putIfAbsent(namespace, "AMZN", StockDto(ticker = "AMZN", lastPrice = BigDecimal.valueOf(84.92))).subscribe()

        val koreaStockMap = buildMap<String, StockDto>{
            put("MSFT", StockDto(ticker = "MSFT", lastPrice = BigDecimal.valueOf(240.05)))
            put("AAPL", StockDto(ticker = "AAPL", lastPrice = BigDecimal.valueOf(132.37)))
            put("AMZN", StockDto(ticker = "AMZN", lastPrice = BigDecimal.valueOf(84.92)))
        }

        koreaStockMap.map{
            val ticker = it.key

            StepVerifier.create(hash.get(namespace, ticker))
                .expectSubscription()
                .expectNextMatches { it.ticker == ticker }
                .expectComplete()
                .verify()
        }
    }

}