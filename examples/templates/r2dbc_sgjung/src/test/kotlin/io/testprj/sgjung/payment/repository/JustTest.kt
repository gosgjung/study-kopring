package io.testprj.sgjung.payment.repository

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import reactor.test.StepVerifier

@SpringBootTest
class JustTest {

    @Autowired
    lateinit var repository: BookRepository

    @Test
    fun `1`(){
        val all = repository.findAll()
        StepVerifier.create(all)
            .verifyComplete()
    }
}