package io.study.gosgjung.reactive_redis.study

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.redis.core.ReactiveRedisTemplate
import reactor.test.StepVerifier

@SpringBootTest
class ReactiveRedisHashTest {

    @Autowired
    @Qualifier("playerAgeMap")
    lateinit var playerAgeMap : ReactiveRedisTemplate<String, String>

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

}